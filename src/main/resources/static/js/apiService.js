const API_BASE_URL = 'http://localhost:8080/api/v1';

// Helper function for API calls
async function fetchData(url, options = {}) {
    try {
        const response = await fetch(`${API_BASE_URL}${url}`, {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...(options.headers || {})
            }
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Request failed');
        }

        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Hotel API
export async function getFeaturedHotels() {
    return fetchData('http://localhost:8080/api/v1/hotels/get');
}

export async function getHotelById(hotelId) {
    return fetchData(`/hotels/getById/${hotelId}`);
}

export async function searchHotels(query) {
    return fetchData(`/hotels/search?query=${query}`);
}

// Room API
export async function getHotelRooms(hotelId) {
    return fetchData(`/rooms/get?hotelId=${hotelId}`);
}

export async function getRoomById(roomId) {
    return fetchData(`/rooms/getRooID/${roomId}`);
}

// Taxi API
export async function getAvailableTaxis() {
    return fetchData('/taxi/available');
}

export async function getTaxiById(taxiId) {
    return fetchData(`/taxi/getTaxiId/${taxiId}`);
}

// Tour API
export async function getFeaturedTours() {
    return fetchData('/tour-packages/getAll');
}

export async function getTourById(tourId) {
    return fetchData(`/tour-packages/${tourId}`);
}

// Booking API
export async function createBooking(bookingData, token) {
    return fetchData('/bookings', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(bookingData)
    });
}

export async function confirmBooking(bookingId, paymentData, token) {
    return fetchData(`/bookings/${bookingId}/confirm`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(paymentData)
    });
}

export async function getUserBookings(email, token) {
    return fetchData(`/bookings/user/${email}`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
}

// Payment API
export async function createPaymentIntent(amount, token) {
    return fetchData('/payments/create-payment-intent', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ amount, currency: 'usd' })
    });
}

// Auth API
export async function loginUser(credentials) {
    return fetchData('/auth/authenticate', {
        method: 'POST',
        body: JSON.stringify(credentials)
    });
}

export async function registerUser(userData) {
    return fetchData('/user/register', {
        method: 'POST',
        body: JSON.stringify(userData)
    });
}

// User API
export async function getUserProfile(token) {
    return fetchData('/user/profile', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
}