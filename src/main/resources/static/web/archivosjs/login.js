

const { createApp } = Vue

createApp({
    data() {
        return {
        inputEmail: '',
        inputPassword: '',
        newName: '',
        newLast: '',
        newEmail: '',
        newPassword: '',
        };
    },
    created() {
    },
    mounted() {},
    methods: {
        login(){
            axios.post('/api/login',`email=${this.inputEmail}&password=${this.inputPassword}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(()=>window.location.href="/web/dashboard2.html")
    },
        singUp(){
            axios.post('/api/clients',`firstName=${this.newName}&lastName=${this.newLast}&email=${this.newEmail}&password=${this.newPassword}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(()=> axios.post('/api/login',`email=${this.newEmail}&password=${this.newPassword}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}))
            .then(()=>axios.post('/api/clients/current/accounts/', `clientEmail=${this.newEmail}`,{headers: {'Content-type': 'application/x-www-form-urlencoded'}}) )
            .then(()=>window.location.href="/web/dashboard2.html")
    },
},
    computed: {},
    update: {}
}).mount("#app");

const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

sign_up_btn.addEventListener("click", () => {
  container.classList.add("sign-up-mode");
});

sign_in_btn.addEventListener("click", () => {
  container.classList.remove("sign-up-mode");
});
