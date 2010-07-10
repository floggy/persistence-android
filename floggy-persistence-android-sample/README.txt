HelloFlashlight is a simple sample application.

It was created to demonstrate the minimal pom.xml setup needed to use the maven-android-plugin
and is used as an example the book Maven: The Complete Reference

See the book chapter for more details.

The following steps were done for the project creation.

- setup of Java, Maven, Android SDK and Android artifacts
- created project with Android SDK using Eclipse tool (new android project...)
- some hacking to have a little flashlight application with buttons to change color
- added minimal pom.xml file
- built application with running emulator

mvn clean install android:deploy


