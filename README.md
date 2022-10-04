<img src="https://1.bp.blogspot.com/-ozOGH_S7zRw/YXmIexkIUqI/AAAAAAAARJw/QjXHsKGzUhAUSy9ocwFBnyckWAOOWcplgCLcBGAsYHQ/s0/Introducing-Media3-SOCIAL%2B%25282%2529.png">

# SimpleMediaPlayer

With the arrival of media3 and for professional reasons, I have had to do a lot of research on the use of media3 for background audio playback using one of the different available services such as **MediaSessionService**, **MediaLibraryService** and **MediaBrowserService**. Also, I needed to display notifications on both the status bar and the lock screen.

During the time that I was documented, I realized that there were few clear examples of the use of these services. Including those available in Android's own repositories (https://github.com/android/uamp, https://github.com/androidx/media), they are all difficult to follow and understand if what you are looking for it is simplicity and only having the necessary features to play music in the background with notifications.

**Goal**

The objective of creating this repository is to present a simpler approach, creating only the necessary classes to comply with the features that I will describe in the next point, but without neglecting a logical, meaningful and reactive structure.

I have not focused on UI, although I will update it to show the potential that a clean, simple service with features that could be assimilated to those offered by Spotify (with a bit of evolution) can have.

**Features**
- Modularized service to abstract dependencies.
- Play music using the internal ExoPlayer client in media3.
- MediaSessionService in the background that will allow us to have a shared playback service so that it can be played anywhere in the application.
- Notifications both in the status bar and on the lock screen with media controls.
- Updating playback status both in notifications and in-app view.

These features are specific to the example, but I'm really going to put the emphasis on the service, the control of it, and the notifications, classes that I will detail below, which are found in the service module. 

## player-service Module

### SimpleMediaService (only 42 lines)

**SimpleMediaService** inherits from **MediaSessionService** and feeds on hilt-injected classes (see **SimpleMediaModule** for more details). The injected classes are as follows:
- **ExoPlayer**: playback client that will be shared in order to control common states.
- **MediaSession**: this is the most important class because it is what creates the magic to be able to share the replay session with whoever needs it, including service and notifications.
- **SimpleMediaNotificationManager**: is a class of its own that I will detail in the next point.

### SimpleMediaNotificationManager (only 71 lines)

**SimpleMediaNotificationManager** has three main functions:
- Create a notification channel.
- Start the background service by assigning an ID and a notification.
- Create the notification by assigning both the MediaSession **Token** and the **player** to it. Both are essential to be able to control the audio with notifications and to be able to see its progress.

In this class is where we can create our custom notification with the controls, icons or images that we want.

### SimpleMediaServiceHandler (only 105 lines)

**SimpleMediaServiceHandler** is a bridge to be able to communicate the application with the service, because the only common dependency that is necessary to have is the one corresponding to the MediaSession. 

Two sealed classes are used that encapsulate the actions and events necessary to coordinate the state of the playback:
- **PlayerEvent**: contains the actions performed by the user and through the **onPlayerEvent** method the state of the player is controlled.
- **SimpleMediaState**: contains the states that will be emitted through a **StateFlow**. In the example the VM collects any emitted changes and thus updates the custom controls.

Besides, it is in charge of arranging the necessary methods to add the MediaItem, be it one or a list.

## Conclusions

A simple and clear playback service with notifications, with a reactive architecture. I will continue checking improvements, this is still an imperfect approach (nothing is perfect), improvable but that provides what is necessary without complications.

## Next Updates
- Improve UI and add a secondary screen with a player sharing the same MediaSession.
- Add metadata to the MediaItem to display images in notifications.
