# QR Code Scanner Feature

## Changes Made

### 1. Updated Relying Party ID
- Changed `RELYING_PARTY_ID` from `dashlane-passkey-demo.glitch.me` to `webauthn-qr-server.vercel.app` in `AccountRepository.kt`

### 2. Added QR Code Scanner Dependencies
Added the following dependencies to `app/build.gradle`:
- `com.google.mlkit:barcode-scanning:17.2.0` - ML Kit Barcode Scanning
- `androidx.camera:camera-camera2:1.2.3` - CameraX Camera2 implementation
- `androidx.camera:camera-lifecycle:1.2.3` - CameraX Lifecycle support
- `androidx.camera:camera-view:1.2.3` - CameraX PreviewView

### 3. Added Camera Permissions
Updated `AndroidManifest.xml` to include:
- Camera feature declaration (not required)
- Camera permission

### 4. Created QR Code Challenge Data Model
Created `QRCodeChallenge.kt` to parse QR code JSON containing:
- `sessionId`: Session identifier
- `type`: "login" or "registration"
- `username`: Optional username
- `rpID`: Relying party identifier
- `origin`: Origin URL
- `challenge`: Challenge string
- `options`: WebAuthn options (challenge, allowCredentials, timeout, userVerification, rpId)

### 5. Created QR Code Scanner UI
Created `QRCodeScanner.kt` with:
- Camera permission request
- Real-time QR code scanning using ML Kit
- Preview view using CameraX
- Cancel button to return to login

### 6. Added QR Code Handling Logic
Updated `LoginViewModel.kt` with:
- `handleQRCodeChallenge()`: Parses QR code and routes to login/registration
- `handleQRLogin()`: Processes login challenges from QR codes
- `handleQRRegistration()`: Processes registration challenges from QR codes

### 7. Integrated QR Scanner with Login Flow
Updated `MainActivity.kt` and `LoginToAccount.kt` to:
- Add "Scan QR Code" button on login page
- Navigate to QR scanner screen
- Handle scanned QR codes and return to login

## Usage

### QR Code Format
The QR code should contain a JSON string matching this format:

```json
{
  "sessionId": "VfjjinYk0rysOTT-0dvw-wF4jGQEdzUv",
  "type": "login",
  "username": "Momo",
  "rpID": "webauthn-qr-server.vercel.app",
  "origin": "https://webauthn-qr-server.vercel.app",
  "challenge": "vvLSiYyVWDmaVIFopCXiz5kCZeK5U0fpT1_dtXLvfOo",
  "options": {
    "challenge": "vvLSiYyVWDmaVIFopCXiz5kCZeK5U0fpT1_dtXLvfOo",
    "allowCredentials": [
      {
        "id": "h8rk6_2mPFuL_TQ6Pc8ZKg",
        "type": "public-key",
        "transports": ["hybrid", "internal"]
      }
    ],
    "timeout": 60000,
    "userVerification": "preferred",
    "rpId": "webauthn-qr-server.vercel.app"
  }
}
```

### Login Flow
1. User taps "Scan QR Code" button on login page
2. App requests camera permission if not granted
3. User scans QR code containing login challenge
4. App parses challenge and authenticates with passkey
5. User is logged in if authentication succeeds

### Registration Flow
1. User taps "Scan QR Code" button on login page
2. App requests camera permission if not granted
3. User scans QR code containing registration challenge (type: "registration" or "create")
4. App creates new passkey with provided challenge
5. Account is created and user can log in

## Testing
To test the QR code feature:
1. Generate a QR code containing the JSON format above
2. Ensure the `rpId` matches `webauthn-qr-server.vercel.app`
3. Scan the QR code using the app
4. Verify authentication/registration works as expected
