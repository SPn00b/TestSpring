<template>
    <q-list>
        <q-item-label header>Essential Links</q-item-label>
        <q-item clickable tag="a" target="" href="#">
            <q-item-section avatar>
                <q-icon name="home" />
            </q-item-section>
            <q-item-section>
                <q-item-label>Home</q-item-label>
                <q-item-label caption>Shree Hari Mandir Home Page</q-item-label>
            </q-item-section>
        </q-item>
        <q-item clickable tag="a" target="" href="#">
            <q-item-section avatar>
                <q-icon name="webhook" />
            </q-item-section>
            <q-item-section>
                <q-item-label>API v1</q-item-label>
                <q-item-label caption>Test API v1 getSomething()</q-item-label>
            </q-item-section>
        </q-item>
        <q-item clickable tag="a" target="" href="#">
            <q-item-section avatar>
                <q-icon name="info" />
            </q-item-section>
            <q-item-section>
                <q-item-label>About Us</q-item-label>
                <q-item-label caption>About Us and Contact details page</q-item-label>
            </q-item-section>
        </q-item>
    </q-list>
</template>

<script>
export default {
    name: 'SideNavigation'
}
</script>