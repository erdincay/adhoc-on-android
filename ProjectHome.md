As part of acquiring the degree of B.Sc. in engineering, there has have developed an ad-hoc library that may be included to any Android app that need to run on a mobile ad-hoc network. The library comprises a routing protocol module (based on AODV) and a setup module for creating and stopping an ad-hoc network on supported Android devices.

The current supported Android phones are HTC Hero, Nexus One and HTC Dream, though only the first two phones are actually tested.

The developed library is only a prototype, meaning that many improvements are available, including letting the library create an ad-hoc network with less manual "interfering". By interfering it is meant that the library e.g. require that the tiwlan.ini file exist (for Hero) on the phone. The aim is to have is to have a library that only require a rooted phone so distributed applications easily may include this library with little work.

Along with the library, there has been developed a very simple Android text messenger app that exploit the functionality of the library as a "proof of concept".

Note: Require a rooted phone!



&lt;hr /&gt;



<b>FAQ</b>

- Where do I find the source code for the Java library and how do I download it?

http://code.google.com/p/adhoc-on-android/wiki/Downloading_the_AdhocLibrary_Java_Library