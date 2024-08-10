import path from "path";
import { defineConfig } from "vite";
import { svelte } from "@sveltejs/vite-plugin-svelte";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [svelte()],
    resolve: {
        alias: {
            $lib: path.resolve("./src/lib"),
        },
    },
    build: {
        outDir: "../main/resources/static/",
        emptyOutDir: true,
    },
    server: {
        proxy: {
            "/api": "http://localhost:8080/",
        },
    },
});
