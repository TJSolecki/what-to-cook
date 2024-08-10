<script lang="ts">
    import { Badge } from "$lib/components/ui/badge/index.js";
    import { TimerIcon } from "lucide-svelte";

    export let name: string;
    export let image: string;
    export let author: string;
    export let totalTime: string;

    function formatTotalTime(time: string): string {
        if (time.startsWith("PT")) {
            return time.substring(2).toLowerCase();
        }
        return time.toLowerCase();
    }
</script>

<button type="button">
    <img id={name} src={image} alt={name} />
    <div class="description">
        <label for={name}>{@html name}</label>
        <span>{author}</span>
    </div>
    <div class="badge">
        <Badge class="flex gap-1 items-center"
            ><TimerIcon />{formatTotalTime(totalTime)}</Badge
        >
    </div>
</button>

<style>
    .badge {
        position: absolute;
        bottom: 0.25rem;
        right: 0.25rem;
    }

    button {
        position: relative;
        padding: 0.5rem;
        display: flex;
        text-align: left;
        gap: 0.5rem;

        &:hover,
        &:active,
        &:focus {
            background: hsl(var(--muted));
        }

        &:hover label,
        &:active label,
        &:focus label {
            text-decoration: underline;
            cursor: pointer;
        }
    }

    .description {
        height: 100%;
        display: flex;
        flex-direction: column;
    }

    img {
        aspect-ratio: 4/3;
        height: 5rem;
        object-fit: cover;
    }

    label {
        font-weight: 700;
        font-size: 0.875rem;
        line-height: 1.25rem;
    }

    span {
        color: hsl(240, 5%, 64.9%);
        font-size: 0.75rem;
        line-height: 1rem;
        font-weight: 600;
    }
</style>
