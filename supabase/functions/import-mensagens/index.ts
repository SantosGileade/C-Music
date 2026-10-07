// Edge Function: busca mensagens do site crentesdabiblia.com.br e devolve
// título + conteúdo já em texto limpo (sem HTML), contornando o bloqueio de
// CORS que o navegador aplicaria numa chamada direta do app.
const corsHeaders = {
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Headers": "authorization, x-client-info, apikey, content-type",
};

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

Deno.serve(async (req) => {
  if (req.method === "OPTIONS") {
    return new Response("ok", { headers: corsHeaders });
  }
  try {
    const body = await req.json().catch(() => ({}));
    const page = body.page || 1;
    const titulo = (body.titulo || "").trim();
    const upstreamBody: Record<string, unknown> = { page };
    if (titulo) upstreamBody.titulo = titulo;
    const resp = await fetch("https://www.crentesdabiblia.com.br/api/downloadsSite", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(upstreamBody),
    });
    if (!resp.ok) {
      throw new Error(`Site retornou status ${resp.status}`);
    }
    const json = await resp.json();
    const data = (json.data || []).map((item: any) => ({
      id: item.id,
      titulo: (item.titulo || "").trim(),
      data: item.data || null,
      local: item.local || "",
      conteudo: htmlToText(item.paragrafos || ""),
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
