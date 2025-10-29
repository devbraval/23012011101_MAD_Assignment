# MediAssist – Medicine Reminder App 

This is my Mobile Application Development assignment project.  
The app reminds users to take their medicines on time using Android alarms and notifications.

## Features

- Add, edit and delete medicines.
- Set how many tablets to take and at what time.
- Get an alarm sound and toast message when it’s time to take medicine.
- Alarm works even if the app is closed (using Foreground Service).
- Shows summary after all tablets are completed.

## Technologies Used

- Language: Kotlin  
- Android SDK: 24 - 34  
- Tools: Android Studio  
- UI: XML layouts with ConstraintLayout and Material Design  
- Background tasks: AlarmManager + BroadcastReceiver + Foreground Service


## Main Files

| File | Purpose |
|------|----------|
| `Medicine.kt` | Model class for storing medicine details |
| `MedicineRepository.kt` | Handles add, remove, and update of medicines |
| `AlarmHelper.kt` | Used to schedule and cancel daily alarms |
| `AlarmBroadcastReceiver.kt` | Runs when the alarm time is reached |
| `AlarmService.kt` | Plays alarm sound and shows notification |
| `DashboardActivity.kt` | Shows list of medicines |
| `AddMedicineActivity.kt` | Used to add new medicine details |
| `AfterCourseActivity.kt` | Shown when all tablets are finished |



## How It Works

1. User adds medicine name, total tablets, and daily time.
2. The app sets exact alarms using `AlarmManager`.
3. When alarm time comes:
   - A toast shows the medicine name.
   - Alarm sound plays for 30 seconds.
4. When all tablets are done, a summary page is shown.


## Permissions Used

```xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
