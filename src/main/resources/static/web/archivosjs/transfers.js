const { createApp } = Vue

createApp({
    data() {
        return {
            id: '',
            params: '',
            queryString: '',
            client: {},
            accounts: [],
            activeAccounts: [],
            transactions: [],
            cards: [],
            account: [],
            accountTransaction: [],
            amount:'',
            accSend:'',
            accRecive:'',
            transDescription:'',
            transAccSelect:'',
        };
    },
    created() {
        this.loadData();
        this.queryString = location.search;
        this.params = new URLSearchParams(this.queryString);
        this.id = this.params.get('id');
        
    },
    mounted() {},
    methods: {
        loadData(){
        axios.get("/api/clients/current/")
        .then(e=>{
            this.client = e.data
            this.accounts = e.data.accounts; 
            this.activeAccounts = this.accounts.filter(account => account.accountActive).sort((a,b) => b.id - a.id)
            console.log(this.activeAccounts)
            this.accountTransactions();
        })
    },
    accountTransactions(){
      axios.get("/api/accounts/"+this.id)
      .then(e=>{
          this.account = e.data
          this.accountTransaction = this.account.transaction.sort((a,b) => b.id - a.id)
          console.log(this.account)
      })
  },
  formatDate(date){
    newDate = new Date(date).toLocaleString();
    return newDate
  },
    newTransactions(){
      Swal.fire({
        title: 'Are you sure?',
        showCancelButton: true,
        confirmButtonText: 'Send',
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
          axios.post("/api/transactions", `amount=${this.amount}&description=${this.transDescription}&numbAccountSend=${this.accSend}&numbAccountRecive=${this.accRecive}`,{ headers: { 'content-type': 'application/x-www-form-urlencoded' } })
          .then(() => Swal.fire("Successful transfer","","success"))
          .then(()=>window.location.href="./dashboard2.html")
          .catch(error => Swal.fire(error.response.data))
        }
      })
    },
        logout(){
        axios.post('/api/logout').then(()=>window.location.href="/web/index.html")
    },
    },
    computed: {},
    update: {}
}).mount("#app");


/* Scripts for css grid dashboard */

$(document).ready(() => {
  addResizeListeners();
  setSidenavListeners();
  setUserDropdownListener();
  renderChart();
  setMenuClickListener();
  setSidenavCloseListener();
});

// Set constants and grab needed elements
const sidenavEl = $('.sidenav');
const gridEl = $('.grid');
const SIDENAV_ACTIVE_CLASS = 'sidenav--active';
const GRID_NO_SCROLL_CLASS = 'grid--noscroll';

function toggleClass(el, className) {
  if (el.hasClass(className)) {
    el.removeClass(className);
  } else {
    el.addClass(className);
  }
}

// User avatar dropdown functionality
function setUserDropdownListener() {
  const userAvatar = $('.header__avatar');

  userAvatar.on('click', function(e) {
    const dropdown = $(this).children('.dropdown');
    toggleClass(dropdown, 'dropdown--active');
  });
}

// Sidenav list sliding functionality
function setSidenavListeners() {
  const subHeadings = $('.navList__subheading'); console.log('subHeadings: ', subHeadings);
  const SUBHEADING_OPEN_CLASS = 'navList__subheading--open';
  const SUBLIST_HIDDEN_CLASS = 'subList--hidden';

  subHeadings.each((i, subHeadingEl) => {
    $(subHeadingEl).on('click', (e) => {
      const subListEl = $(subHeadingEl).siblings();

      // Add/remove selected styles to list category heading
      if (subHeadingEl) {
        toggleClass($(subHeadingEl), SUBHEADING_OPEN_CLASS);
      }

      // Reveal/hide the sublist
      if (subListEl && subListEl.length === 1) {
        toggleClass($(subListEl), SUBLIST_HIDDEN_CLASS);
      }
    });
  });
}

// Draw the chart
function renderChart() {
  const chart = AmCharts.makeChart( "chartdiv", {
    "type": "serial",
    "theme": "light",
    "dataProvider": [ {
      "month": "Jan",
      "visits": 2025
    }, {
      "month": "Feb",
      "visits": 1882
    }, {
      "month": "Mar",
      "visits": 1809
    }, {
      "month": "Apr",
      "visits": 1322
    }, {
      "month": "May",
      "visits": 1122
    }, {
      "month": "Jun",
      "visits": 1114
    }, {
      "month": "Jul",
      "visits": 984
    }, {
      "month": "Aug",
      "visits": 711
    }, {
      "month": "Sept",
      "visits": 665
    }, {
      "month": "Oct",
      "visits": 580
    } ],
    "valueAxes": [ {
      "gridColor": "#FFFFFF",
      "gridAlpha": 0.2,
      "dashLength": 0
    } ],
    "gridAboveGraphs": true,
    "startDuration": 1,
    "graphs": [ {
      "balloonText": "[[category]]: <b>[[value]]</b>",
      "fillAlphas": 0.8,
      "lineAlpha": 0.2,
      "type": "column",
      "valueField": "visits"
    } ],
    "chartCursor": {
      "categoryBalloonEnabled": false,
      "cursorAlpha": 0,
      "zoomable": false
    },
    "categoryField": "month",
    "categoryAxis": {
      "gridPosition": "start",
      "gridAlpha": 0,
      "tickPosition": "start",
      "tickLength": 20
    },
    "export": {
      "enabled": false
    }
  });
}

function toggleClass(el, className) {
  if (el.hasClass(className)) {
    el.removeClass(className);
  } else {
    el.addClass(className);
  }
}

// If user opens the menu and then expands the viewport from mobile size without closing the menu,
// make sure scrolling is enabled again and that sidenav active class is removed
function addResizeListeners() {
  $(window).resize(function(e) {
    const width = window.innerWidth; console.log('width: ', width);

    if (width > 750) {
      sidenavEl.removeClass(SIDENAV_ACTIVE_CLASS);
      gridEl.removeClass(GRID_NO_SCROLL_CLASS);
    }
  });
}

// Menu open sidenav icon, shown only on mobile
function setMenuClickListener() {
  $('.header__menu').on('click', function(e) { console.log('clicked menu icon');
    toggleClass(sidenavEl, SIDENAV_ACTIVE_CLASS);
    toggleClass(gridEl, GRID_NO_SCROLL_CLASS);
  });
}

// Sidenav close icon
function setSidenavCloseListener() {
  $('.sidenav__brand-close').on('click', function(e) {
    toggleClass(sidenavEl, SIDENAV_ACTIVE_CLASS);
    toggleClass(gridEl, GRID_NO_SCROLL_CLASS);
  });
}
