import svelte from "eslint-plugin-svelte";
import tsParser from "@typescript-eslint/parser";
import tseslint from "typescript-eslint";
import svelteParser from "svelte-eslint-parser";
import svelteConfig from "./svelte.config.js";
import unusedImports from "eslint-plugin-unused-imports";

export default [
    ...svelte.configs["flat/prettier"],
    {
        plugins: {
            "unused-imports": unusedImports,
        },
        languageOptions: {
            parser: tsParser,
            parserOptions: {
                // ...
                project: "./tsconfig.json",
                extraFileExtensions: [".svelte"], // This is a required setting in `@typescript-eslint/parser` v4.24.0.
            },
        },
        rules: {
            "unused-imports/no-unused-imports": "error",
        },
    },
    {
        files: ["**/*.svelte", "*.svelte"],
        languageOptions: {
            parser: svelteParser,
            // Parse the `<script>` in `.svelte` as TypeScript by adding the following configuration.
            parserOptions: {
                parser: tsParser,
                svelteConfig,
                svelteFeatures: {
                    stores: true,
                },
            },
        },
    },
    {
        ...tseslint.configs.disableTypeChecked,
        files: ["**/*.js"],
    },
];
