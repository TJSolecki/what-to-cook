import { writable } from "svelte/store";

// -1 implies user is not authenticated
export const userId = writable<number>(-1);
