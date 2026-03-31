# Future Engineering Improvements

- **Enforce Roles**: Lock down the word management (CRUD) to ADMINs only. Regular users should only be able to use the filtering service.
- **Soft Deletes**: Use a flag instead of deleting words for real. It's safer if someone accidentally removes a word.
- **Config Profiles**: Split settings for dev, test, and production so we don't mess up the live database during testing
- **Rate Limiting**: Limit how many requests a user can make to stop people from spamming the filter.
- **Latency Tracking**: Log how long each request takes in the interceptor. Good for spotting slow database queries early.
- **Strict Validation**: Add length and character checks for new words so we don't get weird emojis or broken regex.
- **Bulk Updates**: Allow adding or deleting a list of words in one go. Much faster for moderators.
- **Refresh Tokens**: Add a way to keep users logged in securely without them having to re-authenticate every day.
