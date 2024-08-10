import typescriptEslint from "@typescript-eslint/eslint-plugin";
import svelte from "eslint-plugin-svelte";
import globals from "globals";
import tsParser from "@typescript-eslint/parser";
import parser from "svelte-eslint-parser";
import path from "node:path";
import { fileURLToPath } from "node:url";
import js from "@eslint/js";
import { FlatCompat } from "@eslint/eslintrc";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const compat = new FlatCompat({
    baseDirectory: __dirname,
    recommendedConfig: js.configs.recommended,
    allConfig: js.configs.all,
});

export default [
    ...compat.extends(
        "airbnb-base/legacy",
        "eslint:recommended",
        "plugin:@typescript-eslint/recommended",
        "plugin:svelte/recommended",
        "prettier",
    ),
    ...svelte.configs["flat/recommended"],
    {
        plugins: {
            "@typescript-eslint": typescriptEslint,
            svelte,
        },

        languageOptions: {
            globals: {
                ...globals.browser,
                ...globals.node,
            },

            parser: tsParser,
            ecmaVersion: 2020,
            sourceType: "module",

            parserOptions: {
                extraFileExtensions: [".svelte"],
            },
        },

        rules: {},
    },
    {
        files: ["**/*.svelte"],

        languageOptions: {
            parser: parser,
            ecmaVersion: 5,
            sourceType: "script",

            parserOptions: {
                parser: "@typescript-eslint/parser",
            },
        },
    },
];
