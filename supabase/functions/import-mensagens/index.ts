// Edge Function: busca mensagens do site crentesdabiblia.com.br e devolve
// título + conteúdo já em texto limpo (sem HTML), contornando o bloqueio de
// CORS que o navegador aplicaria numa chamada direta do app.
//
// Corpo aceito:
//   { pagina: 1, titulo: "", lista: true }  -> só id/título/data/local (leve, para a lista)
//   { id: 592, titulo: "FALE A ESTA..." }   -> a mensagem completa (com conteúdo)
const corsHeaders = {
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Headers": "authorization, x-client-info, apikey, content-type",
};

const POR_PAGINA = 12;

const HTML_ENTITIES: Record<string, string> = {
  "&nbsp;": " ",
  "&amp;": "&",
  "&quot;": '"',
  "&apos;": "'",
  "&#39;": "'",
  "&lt;": "<",
  "&gt;": ">",
};

function htmlToText(html: string): string {
  if (!html) return "";
  let text = html
    .replace(/<\/p>/gi, "\n\n")
    .replace(/<br\s*\/?>/gi, "\n")
    .replace(/<[^>]+>/g, "");
  text = text.replace(/&nbsp;|&amp;|&quot;|&apos;|&#39;|&lt;|&gt;/g, (m) => HTML_ENTITIES[m]);
  text = text.replace(/&#(\d+);/g, (_, code) => String.fromCharCode(parseInt(code, 10)));
  text = text
    .replace(/[ \t]+/g, " ")
    .replace(/ *\n */g, "\n")
    .replace(/\n{3,}/g, "\n\n")
    .trim();
  return text;
}

async function buscarPagina(pagina: number, titulo: string) {
  const corpo: Record<string, unknown> = { pagina };
  if (titulo) corpo.titulo = titulo;
  const resp = await fetch("https://www.crentesdabiblia.com.br/api/downloadsSite", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(corpo),
  });
  if (!resp.ok) {
    throw new Error(`Site retornou status ${resp.status}`);
  }
  return await resp.json();
}

Deno.serve(async (req) => {
  if (req.method === "OPTIONS") {
    return new Response("ok", { headers: corsHeaders });
  }
  try {
    const body = await req.json().catch(() => ({}));
    const pagina = Number(body.pagina || body.page || 1) || 1;
    const titulo = (body.titulo || "").trim();
    const lista = body.lista === true;
    const id = body.id ? Number(body.id) : null;

    const json = await buscarPagina(pagina, titulo);
    let itens = json.data || [];

    if (id) {
      let achou = itens.find((i: any) => i.id === id);
      const totalPaginas = Math.ceil((json.total || 0) / POR_PAGINA);
      for (let p = pagina + 1; !achou && p <= totalPaginas && p <= pagina + 5; p++) {
        const outra = await buscarPagina(p, titulo);
        achou = (outra.data || []).find((i: any) => i.id === id);
      }
      itens = achou ? [achou] : [];
    }

    const data = itens.map((item: any) => ({
      id: item.id,
      titulo: (item.titulo || "").trim(),
      data: item.data || null,
      local: item.local || "",
      ...(lista ? {} : { conteudo: htmlToText(item.paragrafos || "") }),
    }));
    return new Response(
      JSON.stringify({ success: true, data, total: json.total || 0 }),
      { headers: { ...corsHeaders, "Content-Type": "application/json" } },
    );
  } catch (e) {
    return new Response(
      JSON.stringify({ success: false, error: String(e) }),
      { status: 500, headers: { ...corsHeaders, "Content-Type": "application/json" } },
    );
  }
});
