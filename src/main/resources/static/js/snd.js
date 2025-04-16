emailjs.init("pYgNfUOuj3l8ITfmY");

// Newsletter form submission
document.getElementById('newsletter-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const userEmail = document.getElementById('user_email').value;

    // Send the email using EmailJS
    emailjs.send("service_ixd0sqf", "template_v7rl8pj", {
        user_email: userEmail
    })
        .then(function(response) {
            console.log('SUCCESS!', response.status, response.text);
            alert('Thank you for subscribing to our newsletter!');
            document.getElementById('newsletter-form').reset();
        }, function(error) {
            console.log('FAILED...', error);
            alert('Subscription failed. Please try again later.');
        });
});