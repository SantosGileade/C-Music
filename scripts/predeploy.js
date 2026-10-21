const fs = require('fs');

fs.mkdirSync('dist/img', { recursive: true });

const build = String(Date.now());
const html = fs.readFileSync('index.html', 'utf8');
if (!html.includes("const APP_BUILD='__BUILD__'")) {
  throw new Error('Marcador APP_BUILD nao encontrado em index.html');
}
fs.writeFileSync('dist/index.html', html.replace("const APP_BUILD='__BUILD__'", "const APP_BUILD='" + build + "'"));

fs.copyFileSync('manifest.json', 'dist/manifest.json');
fs.copyFileSync('sw.js', 'dist/sw.js');
fs.readdirSync('img').forEach((f) => fs.copyFileSync('img/' + f, 'dist/img/' + f));
console.log('Build', build);
