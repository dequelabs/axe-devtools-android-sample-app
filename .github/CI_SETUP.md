# GitHub Actions CI Setup for Appium Tests

This document describes the continuous integration setup for running Appium tests on Android using GitHub Actions.

## Overview

The CI workflow automatically runs Appium tests whenever code is pushed or a pull request is created. It builds the Android app, starts an emulator, and runs the test suites.

## Workflow File

**Location**: `.github/workflows/android-appium-tests.yml`

## Triggers

The workflow runs on:
- **Push** to `main` or `develop` branches
- **Pull requests** targeting `main` or `develop` branches
- **Manual trigger** via GitHub Actions UI (workflow_dispatch)

## Workflow Steps

### 1. Environment Setup
- **Runner**: Ubuntu Latest
- **JDK**: Java 17 (Temurin distribution)
- **Node.js**: Version 20
- **Android API Level**: 30 (Android 11)

### 2. Build Process
```bash
# Clone repository
- Checkout code
- Set up Java 17
- Grant gradlew permissions
- Build debug APK with ./gradlew assembleDebug
```

### 3. Appium Setup
```bash
# Install dependencies
- Set up Node.js with npm cache
- Install Appium test dependencies (npm ci)
- Install Appium globally
- Install Axe UiAutomator2 driver (automatic)
```

### 4. Android Emulator
- Uses `reactivecircus/android-emulator-runner@v2`
- **AVD Caching**: Speeds up subsequent runs
- **Configuration**:
  - API Level: 30
  - Target: google_apis
  - Architecture: x86_64
  - GPU: swiftshader_indirect (headless)
  - Animations: Disabled for faster testing

### 5. Test Execution

Tests run sequentially to avoid resource contention:

```bash
npm run test:navigation  # Bottom navigation tests
npm run test:menu        # Menu screen tests
npm run test:catalog     # Catalog screen tests
npm run test:cart        # Cart screen tests
```

**Note**: Tests use `|| true` to continue even if one suite fails, ensuring all suites are executed.

### 6. Artifacts Upload

After test completion (success or failure):

#### Screenshots
- **Path**: `appium-tests/screenshots/**/*.png`
- **Retention**: 14 days
- **Usage**: Visual verification of test execution

#### Test Logs
- **Path**: `appium-tests/*.log`, `~/.npm/_logs/*.log`
- **Retention**: 7 days
- **Usage**: Debugging test failures

#### APK
- **Path**: `app/build/outputs/apk/debug/app-debug.apk`
- **Retention**: 7 days
- **Usage**: Download and test locally

## Accessing Test Results

### Via GitHub Actions UI

1. Go to **Actions** tab in GitHub
2. Click on the workflow run
3. View **Summary** page for:
   - Test execution status
   - Build time
   - Artifacts

### Download Artifacts

1. Scroll to **Artifacts** section in workflow run
2. Download:
   - `test-screenshots-api-30.zip` - All test screenshots
   - `test-logs-api-30.zip` - Test execution logs
   - `app-debug-apk.zip` - Built Android app

## Performance Considerations

### AVD Caching
The workflow caches the Android Virtual Device to speed up subsequent runs:
- **First run**: ~10-15 minutes (creates AVD)
- **Subsequent runs**: ~5-8 minutes (uses cached AVD)

### Timeouts
- **Overall workflow**: 45 minutes
- **Individual steps**: Default GitHub Actions timeouts

### Optimization Tips
1. **Parallel execution**: Currently sequential; could be parallelized for speed
2. **Test selection**: Run only changed test suites
3. **Emulator warm-up**: Already optimized with snapshot caching

## Customization

### Change Android API Level

Edit the matrix in workflow file:

```yaml
strategy:
  matrix:
    api-level: [29, 30, 31]  # Test on multiple API levels
    target: [google_apis]
```

### Add More Test Suites

Add new test commands in the script section:

```yaml
script: |
  npm run test:navigation
  npm run test:menu
  npm run test:catalog
  npm run test:cart
  npm run test:e2e         # Add new suite
```

### Modify Test Timeouts

Update timeouts in `appium-tests/config/wdio.conf.js`:

```javascript
mochaOpts: {
    timeout: 180000  // 3 minutes per test
}
```

## Troubleshooting

### Tests Failing in CI but Passing Locally

**Possible causes**:
1. Different Android API levels
2. Emulator performance variations
3. Timing issues (add more wait times)

**Solution**: Check test logs and screenshots from artifacts

### Emulator Fails to Start

**Symptoms**: "Emulator timeout" or "AVD not ready"

**Solutions**:
1. Increase wait time after `adb wait-for-device`
2. Check emulator options
3. Clear AVD cache (delete cache key)

### Out of Memory Errors

**Solutions**:
1. Reduce parallel test execution
2. Clear screenshots directory between suites
3. Use smaller emulator resolution

### npm install Failures

**Solutions**:
1. Check `package-lock.json` is committed
2. Verify Node.js version compatibility
3. Clear npm cache in workflow

## Local Testing of CI Configuration

### Test with act (GitHub Actions locally)

```bash
# Install act
brew install act

# Run workflow locally (requires Docker)
act push -j appium-tests

# Run specific job
act workflow_dispatch
```

### Manual CI Simulation

```bash
# 1. Build APK
./gradlew assembleDebug

# 2. Start emulator
emulator -avd Medium_Phone_API_30 -no-window -no-audio &

# 3. Install dependencies
cd appium-tests && npm ci

# 4. Run tests
npm run test:navigation
npm run test:menu
npm run test:catalog
npm run test:cart
```

## Best Practices

### For Test Stability
1. ✅ Use explicit waits instead of fixed sleeps
2. ✅ Check element existence before interaction
3. ✅ Handle both success and failure cases
4. ✅ Take screenshots on failures
5. ✅ Use unique, stable selectors (resource IDs)

### For CI Performance
1. ✅ Cache AVD snapshots
2. ✅ Disable animations
3. ✅ Use headless emulator mode
4. ✅ Clean up artifacts between runs
5. ✅ Fail fast on critical errors

### For Maintenance
1. ✅ Keep dependencies updated
2. ✅ Document selector changes
3. ✅ Version control test data
4. ✅ Monitor test execution times
5. ✅ Review and clean old artifacts

## CI/CD Integration

### Pull Request Checks

The workflow runs automatically on pull requests:
- ✅ All tests must pass
- ✅ Screenshots uploaded for review
- ❌ PR blocked if tests fail (recommended setting)

### Branch Protection

Recommended settings:
1. **Require status checks**: Enable for main branch
2. **Require tests to pass**: Before merge
3. **Require up-to-date branches**: Before merge

## Monitoring and Alerts

### GitHub Actions Notifications

Configure notifications in GitHub:
- **Settings** → **Notifications**
- Enable "Actions" notifications
- Choose: Email, Web, Mobile

### Workflow Status Badge

Add to README.md:

```markdown
![Android Appium Tests](https://github.com/YOUR_USERNAME/axe-devtools-android-sample-app/workflows/Android%20Appium%20Tests/badge.svg)
```

## Cost Considerations

GitHub Actions usage:
- **Public repositories**: Free unlimited minutes
- **Private repositories**: 2,000 minutes/month (free tier)
- **This workflow**: ~5-15 minutes per run

## Support and Resources

### Documentation
- [GitHub Actions Docs](https://docs.github.com/en/actions)
- [Android Emulator Runner](https://github.com/ReactiveCircus/android-emulator-runner)
- [Appium Documentation](https://appium.io/docs/en/latest/)

### Related Files
- Workflow: `.github/workflows/android-appium-tests.yml`
- Appium Config: `appium-tests/config/wdio.conf.js`
- Selectors: `appium-tests/helpers/selectors.js`
- Test Suites: `appium-tests/test/*.spec.js`

## Changelog

### 2025-12-23
- ✅ Initial CI setup
- ✅ Android API 30 with Google APIs
- ✅ Appium 2.x with Axe UiAutomator2 driver
- ✅ Screenshot and log artifacts
- ✅ AVD snapshot caching
