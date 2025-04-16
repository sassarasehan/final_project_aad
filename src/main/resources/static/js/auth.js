const loginForm = document.getElementById('login-form');
const registerForm = document.getElementById('register-form');
const showRegister = document.getElementById('show-register');
const showLogin = document.getElementById('show-login');
const closeModalBtns = document.querySelectorAll('.close-modal');

// Login form submission
loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await loginUser({ email, password });

        if (response.code === 201) {
            const { token, data: userData } = response;

            // Store token and user data
            localStorage.setItem('token', token);
            localStorage.setItem('user', JSON.stringify(userData));

            // Close modal and update UI
            document.getElementById('login-modal').style.display = 'none';
            updateAuthUI();

            // Show success message
            showSuccess('Login successful!');
        } else {
            showError(response.message || 'Login failed');
        }
    } catch (error) {
        showError(error.message || 'Login failed. Please try again.');
    }
});

// Register form submission
registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const userData = {
        name: document.getElementById('reg-name').value,
        email: document.getElementById('reg-email').value,
        password: document.getElementById('reg-password').value,
        phone: document.getElementById('reg-phone').value,
        role: 'USER' // Default role
    };

    try {
        const response = await registerUser(userData);

        if (response.code === 201) {
            // Close modal and show login
            document.getElementById('register-modal').style.display = 'none';
            document.getElementById('login-modal').style.display = 'flex';

            // Clear form
            registerForm.reset();

            showSuccess('Registration successful! Please login.');
        } else {
            showError(response.message || 'Registration failed');
        }
    } catch (error) {
        showError(error.message || 'Registration failed. Please try again.');
    }
});

// Toggle between login and register modals
showRegister.addEventListener('click', (e) => {
    e.preventDefault();
    document.getElementById('login-modal').style.display = 'none';
    document.getElementById('register-modal').style.display = 'flex';
});

showLogin.addEventListener('click', (e) => {
    e.preventDefault();
    document.getElementById('register-modal').style.display = 'none';
    document.getElementById('login-modal').style.display = 'flex';
});

// Close modals
closeModalBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        document.getElementById('login-modal').style.display = 'none';
        document.getElementById('register-modal').style.display = 'none';
    });
});

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === document.getElementById('login-modal')) {
        document.getElementById('login-modal').style.display = 'none';
    }
    if (e.target === document.getElementById('register-modal')) {
        document.getElementById('register-modal').style.display = 'none';
    }
});

// Show success message
function showSuccess(message) {
    const successDiv = document.createElement('div');
    successDiv.className = 'success-message';
    successDiv.textContent = message;
    document.body.appendChild(successDiv);

    setTimeout(() => {
        successDiv.remove();
    }, 5000);
}