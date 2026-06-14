# Code Formatting Checklist

Before final portfolio commit:

1. Format all Java files in IntelliJ:
   - Windows/Linux: Ctrl + Alt + L
   - macOS: Option + Command + L

2. Optimize imports:
   - Windows/Linux: Ctrl + Alt + O
   - macOS: Control + Option + O

3. Run tests/build:
   ```bash
   mvn clean test
   ```

4. Check current git state:
   ```bash
   git status
   git log --oneline -5
   ```

5. Recommended final commit:
   ```bash
   git add README.md StudentManagementSystem.postman_collection.json CODE_FORMATTING.md
   git commit -m "docs: add README and Postman collection"
   git push origin master
   ```
