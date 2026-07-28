// ================= PAGE NAVIGATION =================

function showPage(pageId, button = null) {

    // Hide every page
    const pages = document.querySelectorAll(".page");

    pages.forEach(page => {
        page.classList.remove("active-page");
    });


    // Show selected page
    const selectedPage = document.getElementById(pageId);

    if (selectedPage) {
        selectedPage.classList.add("active-page");
    }


    // Change active sidebar button
    const buttons = document.querySelectorAll(".nav-btn");

    buttons.forEach(btn => {
        btn.classList.remove("active");
    });

    if (button) {
        button.classList.add("active");
    }


    // Update title
    const titles = {
        dashboard: "Dashboard",
        products: "My Products",
        expiry: "Expiry Tracker",
        analytics: "Fridge Analytics"
    };

    document.getElementById("pageTitle").textContent =
        titles[pageId] || "Smart Fridge";
}


// ================= DATE =================

function updateDate() {

    const now = new Date();

    const options = {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric"
    };

    document.getElementById("currentDate").textContent =
        now.toLocaleDateString("en-IN", options);
}

updateDate();


// ================= ADD PRODUCT MODAL =================

function openAddProduct() {

    document
        .getElementById("productModal")
        .classList.add("show");
}


function closeAddProduct() {

    document
        .getElementById("productModal")
        .classList.remove("show");
}


// ================= ADD PRODUCT =================

function addProduct() {

    const name =
        document.getElementById("productName").value;

    const category =
        document.getElementById("productCategory").value;

    const quantity =
        document.getElementById("productQuantity").value;

    const expiry =
        document.getElementById("productExpiry").value;


    if (!name || !quantity || !expiry) {

        alert("Please fill all product details.");

        return;
    }


    const grid =
        document.getElementById("productGrid");


    const card =
        document.createElement("div");

    card.className = "product-card";


    card.innerHTML = `

        <div class="product-image">
            🥫
        </div>

        <h3>${name}</h3>

        <p>${category}</p>

        <div class="product-details">
            <span>Quantity</span>
            <strong>${quantity}</strong>
        </div>

        <div class="product-details">
            <span>Expires</span>
            <strong>${expiry}</strong>
        </div>

        <div class="fresh-label">
            Fresh
        </div>
    `;


    grid.appendChild(card);


    // Clear form

    document.getElementById("productName").value = "";

    document.getElementById("productQuantity").value = "";

    document.getElementById("productExpiry").value = "";


    closeAddProduct();


    alert("Product added to fridge!");
}


// ================= SEARCH =================

function searchProducts() {

    const input =
        document.getElementById("searchInput")
        .value
        .toLowerCase();


    const products =
        document.querySelectorAll(".product-card");


    products.forEach(product => {

        const name =
            product.querySelector("h3")
            .textContent
            .toLowerCase();


        if (name.includes(input)) {

            product.style.display = "block";

        } else {

            product.style.display = "none";

        }

    });
}
