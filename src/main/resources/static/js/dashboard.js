let allPosts = [];


document.addEventListener("DOMContentLoaded", () => {

    //  Load user from backend (main fix)
    loadUser();

    // 🔍 Search
    document.getElementById("searchInput").addEventListener("input", renderPosts);

    // 🚪 Logout
    document.getElementById("logoutBtn").addEventListener("click", function () {
        localStorage.clear();
        sessionStorage.clear();

        // optional: backend logout (future)
        window.location.href = "login.html";
    });

    // 📡 Load posts
    loadPosts();
});

document.getElementById("logoutBtn").addEventListener("click", function () {

    alert("Logout clicked"); //  test

    fetch("http://localhost:8080/logout", {
        method: "POST",
        credentials: "include"
    })
    .then(() => {
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "index.html";
    })
    .catch(err => console.log(err));
});


// ================= USER LOAD =================
function loadUser() {
    fetch("http://localhost:8080/api/user", {
        credentials: "include"   //  VERY IMPORTANT
    })
    .then(res => res.json())
    .then(user => {
        console.log("USER:", user);

        //  Agar user nahi hai → login page bhejo
        if (!user) {
            window.location.href = "login.html";
            return;
        }

        // 👤 Name show
        document.getElementById("welcomeName").innerText = user.name;

        // 🖼 Profile pic
        if (user.profilePic) {
            document.getElementById("userAvatar").src = user.profilePic;
        } else {
            // fallback image
            document.getElementById("userAvatar").src = "https://ui-avatars.com/api/?name=" + user.name;
        }
    })
    .catch(err => {
        console.log("User fetch error:", err);
        window.location.href = "login.html";
    });
}


// ================= LOAD POSTS =================
async function loadPosts() {
    try {
        const res = await fetch("http://localhost:8080/api/posts");
        allPosts = await res.json();
        renderPosts();
    } catch (err) {
        console.error("Error loading posts:", err);
    }
}


// ================= RENDER POSTS =================
function renderPosts() {

    const container = document.getElementById("postsContainer");
    const search = document.getElementById("searchInput").value.toLowerCase();

    container.innerHTML = "";

    const filtered = allPosts.filter(post =>
        post.content.toLowerCase().includes(search)
    );

    if (filtered.length === 0) {
        container.innerHTML = "<p style='text-align:center'>No posts found</p>";
        return;
    }

    filtered.forEach(post => {

        const div = document.createElement("div");
        div.className = "post-card";

        div.innerHTML = `
            <div><b>${post.category}</b></div>
            <div>${post.content}</div>
            <small>${new Date(post.createdAt).toLocaleString()}</small>
        `;

        container.appendChild(div);
    });
}