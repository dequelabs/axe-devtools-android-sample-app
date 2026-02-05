# Test Reports

## HTML Test Report

After running tests, an HTML report is automatically generated with all test results, screenshots, and pass/fail status.

### Viewing the Report

The HTML report is located at:
```
test-reports/mochawesome/report.html
```

Open it in your browser to view:
- Test pass/fail summary
- Individual test case details
- Embedded screenshots
- Test execution timeline
- Error messages and stack traces

### Running Tests with Reports

All test commands automatically generate HTML reports:

```bash
# Run all navigation tests
npm run test:navigation

# Run all tests
npm run test

# Run specific test suites
npm run test:menu
npm run test:catalog
npm run test:cart
npm run test:e2e
```

### Manual Report Generation

If you need to regenerate the HTML report from existing JSON data:

```bash
npm run report:generate
```

### Report Configuration

The report configuration is in `config/wdio.conf.js`:
- Reporter: Mochawesome
- Output directory: `test-reports/mochawesome/`
- Screenshots: Embedded inline in HTML
- Report title: "Axe DevTools Android Test Report"

### GitHub Actions

HTML reports are automatically uploaded as artifacts in GitHub Actions workflows and can be downloaded from the workflow run page.
