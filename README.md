# ICT342 Fusion App

This Android application displays the user's personal Spotify music statistics. Three aggregated graphs are used: one for top artists, top tracks and another for top genres.

There is a singleton class (UserDataLab) that contains access to all the data models. There are three main data models: A top artist, a top track and a profile java class. The top track data class contains the genre data as a class variable. The profile data class contains the user's name and methods to retrieve their Spotify profile.

The ui module is where all the code for displaying the data is contained. It uses the Model View ViewModel (MVVM) pattern.
