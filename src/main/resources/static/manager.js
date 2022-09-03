const { createApp } = Vue

createApp({
    data() {
        return {
            arrayClientes: [],
            valueInputName: '',
            valueInputLastName: '',
            valueInputEmail: '',

        };
    },
    created() {
        this.loadData()
    },
    mounted() { },
    methods: {
        loadData() {
            axios.get('/api/clients')
                .then((response) => {
                    this.arrayClientes = response.data
                })
        },
        addClient() {
            axios.post('/rest/clients', {
                firstName: this.valueInputName,
                lastName: this.valueInputLastName,
                email: this.valueInputEmail
            })
                .then((response) => {
                    this.arrayClientes.push(response.data);
                })
        },
        postClient() {
            axios.post('/rest/clients')
                .then(this.loadData())
        },
        deleteClient(client){
            axios.delete("/rest/clients/"+client.id)
                .then(response =>{
                    console.log(response)
                    this.loadData()
                })
        },
        editClient(clientSelected){
            console.log(clientSelected)
            let newEmail = prompt("New Email")
            client = {
                email: newEmail
            }
            axios.patch("/rest/clients/"+clientSelected.id, client)
            .then(response=>{
                console.log(response)
                this.loadData()})
        },

},
    computed: {},
    update: {}
}).mount("#app");
