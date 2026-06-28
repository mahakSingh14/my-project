// =============================
// LANGUAGE MODAL FUNCTIONALITY
// =============================

document.addEventListener("DOMContentLoaded", function() {
    
    // Initialize translations first
    if (typeof initTranslations === 'function') {
        initTranslations();
    }
    
    // ===== ELEMENTS =====
    const logoToggle = document.getElementById("logoToggle");
    const languageModal = document.getElementById("languageModal");
    const closeModalBtn = document.getElementById("closeModal");
    const languageList = document.getElementById("languageList");
    
    // ===== SHOW USER NAME =====
    displayUserName();
    
    // ===== OPEN MODAL ON LOGO CLICK =====
    if (logoToggle) {
        logoToggle.addEventListener("click", function(e) {
            e.preventDefault();
            e.stopPropagation();
            console.log("🌐 Logo clicked - opening language modal");
            
            if (languageModal) {
                languageModal.style.display = "block";
                loadLanguageOptions(); // Load language list
                highlightCurrentLanguage();
            }
        });
    }
    
    // ===== CLOSE MODAL FUNCTIONS =====
    if (closeModalBtn) {
        closeModalBtn.addEventListener("click", function() {
            languageModal.style.display = "none";
        });
    }
    
    window.addEventListener("click", function(e) {
        if (e.target === languageModal) {
            languageModal.style.display = "none";
        }
    });
    
    // Escape key to close
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape' && languageModal.style.display === 'block') {
            languageModal.style.display = 'none';
        }
    });
    
    // ===== LOAD LANGUAGE OPTIONS =====
    function loadLanguageOptions() {
        if (!languageList) return;
        
        // All Indian languages with flags
        const languages = [
            { code: 'en', name: 'English', flag: '🇬🇧' },
            { code: 'hi', name: 'हिन्दी', flag: '🇮🇳' },
            { code: 'mr', name: 'मराठी', flag: '🇮🇳' },
            { code: 'bn', name: 'বাংলা', flag: '🇮🇳' },
            { code: 'ta', name: 'தமிழ்', flag: '🇮🇳' },
            { code: 'te', name: 'తెలుగు', flag: '🇮🇳' },
            { code: 'gu', name: 'ગુજરાતી', flag: '🇮🇳' },
            { code: 'pa', name: 'ਪੰਜਾਬੀ', flag: '🇮🇳' },
            { code: 'ur', name: 'اردو', flag: '🇮🇳' },
            { code: 'kn', name: 'ಕನ್ನಡ', flag: '🇮🇳' },
            { code: 'ml', name: 'മലയാളം', flag: '🇮🇳' },
            { code: 'or', name: 'ଓଡ଼ିଆ', flag: '🇮🇳' },
            { code: 'as', name: 'অসমীয়া', flag: '🇮🇳' }
        ];
        
        languageList.innerHTML = '';
        
        languages.forEach(lang => {
            const div = document.createElement('div');
            div.className = 'lang-option';
            div.dataset.lang = lang.code;
            div.innerHTML = `${lang.flag} ${lang.name}`;
            
            // Add click event for language selection
            div.addEventListener('click', function() {
                const selectedCode = this.dataset.lang;
                const selectedName = this.innerText;
                
                // Use translation function
                if (typeof setLanguage === 'function') {
                    setLanguage(selectedCode);
                } else {
                    localStorage.setItem('selectedLanguage', selectedCode);
                }
                
                // Show success message
                showToast(`✅ ${selectedName} selected`);
                
                // Close modal
                languageModal.style.display = 'none';
            });
            
            languageList.appendChild(div);
        });
    }
    
    // ===== HIGHLIGHT CURRENT LANGUAGE =====
    function highlightCurrentLanguage() {
        const currentLang = localStorage.getItem('selectedLanguage') || 'en';
        
        setTimeout(() => {
            document.querySelectorAll('.lang-option').forEach(opt => {
                if (opt.dataset.lang === currentLang) {
                    opt.classList.add('active');
                    
                    // Scroll to active language
                    opt.scrollIntoView({ behavior: 'smooth', block: 'center' });
                } else {
                    opt.classList.remove('active');
                }
            });
        }, 100);
    }
    
    // ===== DISPLAY USER NAME =====
    function displayUserName() {
        const userName = localStorage.getItem("userName") || "Writer";
        const userElement = document.getElementById("userName");
        const welcomeElement = document.getElementById("welcomeUser");
        
        if (userElement) {
            userElement.innerText = userName;
        }
        
        if (welcomeElement) {
            welcomeElement.innerText = `Welcome, ${userName}`;
        }
    }
    
    // ===== TOAST NOTIFICATION =====
    function showToast(message) {
        // Remove existing toast
        const existingToast = document.querySelector('.toast');
        if (existingToast) existingToast.remove();
        
        // Create new toast
        const toast = document.createElement('div');
        toast.className = 'toast';
        toast.textContent = message;
        toast.style.cssText = `
            position: fixed;
            bottom: 20px;
            right: 20px;
            background: #333;
            color: white;
            padding: 12px 24px;
            border-radius: 8px;
            z-index: 9999;
            animation: slideIn 0.3s ease;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        `;
        
        document.body.appendChild(toast);
        
        // Remove after 2 seconds
        setTimeout(() => {
            toast.style.animation = 'slideOut 0.3s ease';
            setTimeout(() => toast.remove(), 300);
        }, 2000);
    }
    
    // ===== CATEGORY BUTTONS =====
    document.querySelectorAll('.pill').forEach(btn => {
        btn.addEventListener('click', function() {
            document.querySelectorAll('.pill').forEach(p => p.classList.remove('active'));
            this.classList.add('active');
            
            const category = this.innerText;
            showToast(`📌 Showing: ${category}`);
        });
    });
    
    // ===== SEARCH FUNCTIONALITY =====
    const searchInput = document.querySelector('.search-box input');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter' && this.value.trim()) {
                showToast(`🔍 Searching: ${this.value}`);
            }
        });
    }
    
    // ===== FEATURED CARDS =====
    document.querySelectorAll('.featured-card').forEach(card => {
        card.addEventListener('click', function() {
            const title = this.querySelector('h3')?.innerText || 'post';
            showToast(`📖 Opening: ${title}`);
        });
    });
    
    // Add animation styles
    const style = document.createElement('style');
    style.textContent = `
        @keyframes slideIn {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }
        @keyframes slideOut {
            from { transform: translateX(0); opacity: 1; }
            to { transform: translateX(100%); opacity: 0; }
        }
    `;
    document.head.appendChild(style);
    
    console.log("✅ Language module loaded successfully!");
});