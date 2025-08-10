function submitFeedback() {
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const message = document.getElementById("message").value.trim();
    const responseMsg = document.getElementById("responseMsg");

    if (name && email && message) {
        responseMsg.textContent = "Thank you for your feedback, " + name + "!";
        responseMsg.style.color = "green";
        document.getElementById("feedbackForm").reset();
    } else {
        responseMsg.textContent = "Please fill in all fields.";
        responseMsg.style.color = "red";
    }
}
