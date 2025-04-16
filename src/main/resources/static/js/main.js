import {
    getFeaturedHotels,
    getAvailableTaxis,
    getFeaturedTours,
    searchHotels
} from './apiService';

// DOM Elements
const hotelsContainer = document.getElementById('hotels-container');
const taxisContainer = document.getElementById('taxis-container');
const toursContainer = document.getElementById('tours-container');
const searchInput = document.getElementById('searchInput');
const searchBtn = document.getElementById('searchBtn');
const authBtn = document.getElementById('authBtn');
const myBookingsLink = document.getElementById('myBookingsLink');
const hamburger = document.querySelector('.hamburger');
const navLinks = document.querySelector('.nav-links');

// Load data on page load
document.addEventListener('DOMContentLoaded', async () => {
    try {
        // Check auth status
        updateAuthUI();

        // Load hotels
        const hotels = await getFeaturedHotels();
        displayHotels(hotels.slice(0, 3)); // Show only 3 featured hotels

        // Load taxis
        const taxis = await getAvailableTaxis();
        displayTaxis(taxis.slice(0, 3)); // Show only 3 available taxis

        // Load tours
        const tours = await getFeaturedTours();
        displayTours(tours.slice(0, 3)); // Show only 3 featured tours
    } catch (error) {
        console.error('Error loading data:', error);
        showError('Failed to load data. Please try again later.');
    }
});

// Display hotels
function displayHotels(hotels) {
    if (hotels.length === 0) {
        hotelsContainer.innerHTML = '<p class="no-results">No hotels found</p>';
        return;
    }

    hotelsContainer.innerHTML = hotels.map(hotel => `
        <div class="card">
            <div class="card-img">
                <img src="${API_BASE_URL}/hotels/images/${hotel.image}" alt="${hotel.name}" onerror="this.src='images/default-hotel.jpg'">
            </div>
            <div class="card-content">
                <h3 class="card-title">${hotel.name}</h3>
                <p class="card-text"><i class="fas fa-map-marker-alt"></i> ${hotel.address}</p>
                <p class="card-text"><i class="fas fa-phone"></i> ${hotel.phoneNumber}</p>
                <a href="hotel-rooms.html?hotelId=${hotel.hotelId}" class="btn card-btn">View Rooms</a>
            </div>
        </div>
    `).join('');
}

// Display taxis
function displayTaxis(taxis) {
    if (taxis.length === 0) {
        taxisContainer.innerHTML = '<p class="no-results">No taxis available</p>';
        return;
    }

    taxisContainer.innerHTML = taxis.map(taxi => `
        <div class="card">
            <div class="card-img">
                <img src="${API_BASE_URL}/taxi/images/${taxi.image}" alt="${taxi.taxiName}" onerror="this.src='images/default-taxi.jpg'">
            </div>
            <div class="card-content">
                <h3 class="card-title">${taxi.taxiName}</h3>
                <p class="card-text">Driver: ${taxi.driverName}</p>
                <p class="card-text">License: ${taxi.licensePlate}</p>
                <p class="card-text">Location: ${taxi.location}</p>
                <p class="card-text">Status: <span class="${taxi.isAvailable === 'available' ? 'available' : 'unavailable'}">${taxi.isAvailable}</span></p>
                <a href="book-taxi.html?taxiId=${taxi.taxiId}" class="btn card-btn ${taxi.isAvailable !== 'available' ? 'disabled' : ''}">
                    ${taxi.isAvailable === 'available' ? 'Book Now' : 'Not Available'}
                </a>
            </div>
        </div>
    `).join('');
}

// Display tours
function displayTours(tours) {
    if (tours.length === 0) {
        toursContainer.innerHTML = '<p class="no-results">No tours available</p>';
        return;
    }

    toursContainer.innerHTML = tours.map(tour => `
        <div class="card">
            <div class="card-img">
                <img src="${API_BASE_URL}/tour-packages/images/${tour.image}" alt="${tour.packageName}" onerror="this.src='images/default-tour.jpg'">
            </div>
            <div class="card-content">
                <h3 class="card-title">${tour.packageName}</h3>
                <p class="card-text">${tour.description.substring(0, 100)}...</p>
                <p class="card-price">$${tour.price} <span>for ${tour.duration} days</span></p>
                <a href="book-tour.html?packageId=${tour.packageId}" class="btn card-btn">Book Tour</a>
            </div>
        </div>
    `).join('');
}

// Search functionality
searchBtn.addEventListener('click', async () => {
    const query = searchInput.value.trim();
    if (query) {
        try {
            const results = await searchHotels(query);
            displayHotels(results);
        } catch (error) {
            showError('Failed to search. Please try again.');
        }
    }
});

// Update auth UI
function updateAuthUI() {
    const user = JSON.parse(localStorage.getItem('user'));

    if (user) {
        authBtn.textContent = 'My Account';
        authBtn.onclick = () => {
            window.location.href = 'account.html';
        };
        myBookingsLink.style.display = 'block';
    } else {
        authBtn.textContent = 'Login';
        authBtn.onclick = () => {
            document.getElementById('login-modal').style.display = 'flex';
        };
        myBookingsLink.style.display = 'none';
    }
}

// Show error message
function showError(message) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    document.body.appendChild(errorDiv);

    setTimeout(() => {
        errorDiv.remove();
    }, 5000);
}

// Navbar scroll effect
window.addEventListener('scroll', () => {
    if (window.scrollY > 50) {
        document.querySelector('.navbar').classList.add('scrolled');
    } else {
        document.querySelector('.navbar').classList.remove('scrolled');
    }
});

// Mobile menu toggle
hamburger.addEventListener('click', () => {
    navLinks.classList.toggle('active');
});

// Close mobile menu when clicking a link
document.querySelectorAll('.nav-links a').forEach(link => {
    link.addEventListener('click', () => {
        navLinks.classList.remove('active');
    });
});