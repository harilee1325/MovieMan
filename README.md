# MovieMan

This is a movie and tv show review app which uses the themoviedb.org api 
Here you will be able to find all latest tv shows and movies and also the app provides movieman review unit.

Review unit
=> here movieman will provide a review system where the review will be only of the users of the app and not anyone from outside.
=> this feature will help provide a persoanlized touch to the app.


MVP 

MVP (Model View Presenter) pattern is a derivative from the well known MVC (Model View Controller), and one of the most popular patterns to organize the presentation layer in Android Applications.

Model
In an application with a good layered architecture, this model would only be the gateway to the domain layer or business logic. See it as the provider of the data we want to display in the view. Model’s responsibilities include using APIs, caching data, managing databases and so on.

View
The View, usually implemented by an Activity, will contain a reference to the presenter. The only thing that the view will do is to call a method from the Presenter every time there is an interface action.

Presenter
The Presenter is responsible to act as the middle man between View and Model. It retrieves data from the Model and returns it formatted to the View. But unlike the typical MVC, it also decides what happens when you interact with the View.

RXJAVA

Reactive Programming is a programming paradigm oriented around data flows and the propagation of change i.e. it is all about responding to value changes. For example, let’s say we define x = y+z. When we change the value of y or z, the value of x automatically changes. This can be done by observing the values of y and z.

RxJava is a Java based implementation of Reactive Programming.
RxAndroid is specific to Android platform which utilises some classes on top of the RxJava library.

In this project i have made use of the above mentioned desciplines to make the applications user experience as smooth as possible.
