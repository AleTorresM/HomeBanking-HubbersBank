
const { createApp } = Vue

createApp({
    data() {
        return {
        inputEmail: 'sadadsa',
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


const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});