
const { createApp } = Vue

createApp({
    data() {
        return {
            id: '',
            params: '',
            queryString: '',
            account: {},
            transactions: {},
            cards: {},
            transAmount:'',
            transAccSend:'',
            transAccRecive:'',
            transDescription:'',
            transAccSelect:'',
        };
    },
    created() {
        this.queryString = location.search;
        this.params = new URLSearchParams(this.queryString);
        this.id = this.params.get('id');
        this.loadData();
        
    },
    mounted() {},
    methods: {
      loadData(){
        axios.get("/api/accounts/"+this.id)
        .then(e=>{
            this.account = e.data
            this.transactions = this.account.transaction
            this.formateDate(this.transactions)
            console.log(this.account)
        })
    },
        formateDate(e){
            e.forEach(e => {
                e.dateCreation = e.dateCreation.slice(0,10);
            });
        },
        logout(){
          axios.post('/api/logout').then(()=>window.location.href="/web/index.html")
      },
    },
    computed: {},
    update: {}
}).mount("#app");


const navToggle = document.querySelector('.nav-toggle');
const navMenu = document.querySelector('.nav-menu_visible');

navToggle.addEventListener('click', ()=>{
    navMenu.classList.toggle('nav-menu_visible');
})