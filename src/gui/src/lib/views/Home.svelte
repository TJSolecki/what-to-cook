<script lang="ts">
    import Button from "$lib/components/ui/button/button.svelte";
</script>

<div class="hero">
    <div class="hero-text">
        <h1
            class="text-3xl font-semibold tracking-tight transition-colors first:mt-0"
        >
            Share recipes without the hassle
        </h1>
    </div>
    <div class="hero-img">
        <img src="soup.jpg" alt="a bowl of soup" />
    </div>
    <div class="hero-buttons">
        <div class="button-wrapper">
            <Button href="/login" size="lg">Login</Button>
            <Button href="/register" variant="secondary" size="lg"
                >Register</Button
            >
        </div>
    </div>
</div>

<style>
    .hero {
        display: grid;
        gap: 1rem;
    }

    .hero-text {
        display: flex;
        flex-direction: column;
    }

    .hero-img img {
        aspect-ratio: 1/1.15;
        object-fit: cover;
        object-position: top;
        width: 100%;
        max-width: 600px;
    }

    .button-wrapper {
        display: flex;
        flex-direction: column;
        width: 100%;
        max-width: 20rem;
        gap: 1rem;
        justify-content: center;
    }

    .hero-buttons {
        display: flex;
        justify-content: center;
    }

    @media (min-width: 700px) {
        .hero {
            grid-template-columns: 1fr 1fr;
            grid-template-rows: 1fr 1fr 1fr 1fr;
        }
        .hero-img {
            grid-column: 2;
            grid-row: 1/5;
        }
        .hero-text {
            text-align: center;
            margin-top: auto;
            grid-column: 1;
            grid-row: 1;
        }
        .hero-buttons {
            grid-column: 1;
            grid-row: 2;
            margin-bottom: auto;
        }
    }
</style>
