# HeroRoster

This app was developed for tech talk titled "Leveling Up Your Mobile Apps: Levering Third-Party Libraries" given at the University of Texas.

Here is the transcript for the speech:

###So Who is This Guy?

Before we get started, my name’s Adrian Mohnacs and I’m a Full Stack Software Developer at Dante.  We’re a software company that was founded in 1998 and since then we’ve consulted and developed software for companies including MasterCard, Comcast, Verizon, and the Federal Government.  

Currently our company is shifting towards mobile applications and we’re starting with Ouli.  An app that recommends activities in the DC area, it’s the project that I’m helping with.  I graduated from Hanover College in 2014 with a degree in Computer Science and I’ve been programming in Java since college (yay!) and developing Android apps for around two.

Today I’m going to talk to you about the direction that technology is heading and the increasingly competitive market that is developing around it.
I’m going to show you how you can take your application to the next level, decrease development time, and give your app that “wow” factor by leveraging open source third party libraries.
And if this peaks your interest I’m going to show you how you can contribute to the open source community.

###The Rise of Mobile and the World to Come

Who here has an Android phone???
 
Most of you?

Okay that's what I expected.

The reason I say this is because Android’s share in the smartphone market is now around 80%, that’s a 4% rise from 2015 where 1.4 billion smartphones were sold.  Today there are 2.6 billion smartphones globally.  By 2020, there will be 6.1 billion out of the 7.7 billion people. 

That’s roughly 70 percent of the world’s population in five years.  

But everyone I know and I’m sure most of the people you know have a smartphone, so where is this massive growth coming from?  The markets in North America and Europe have pretty much leveled off.  Meaning the growth is going to come from emerging markets.  These markets include include Asia Pacific, the Middle East, and Africa.  

By the time these populations have smartphones, 80% of the traffic on the web will be from mobile devices and average monthly data usage will increase from 2.4 GB to 14 GB.

By 2017 apps will be downloaded more than 268 billion times and will have generated an equally impressive revenue of $77 billion dollars.  A large part of this is the spike in activation rates for Android, which I talked about a second ago.  Because of the consumer app market saturation, the Enterprise segment will likely be the driver behind the app revenue growth in the coming years.  

So...there is going to be a lot of competition coming, but there is also going to be a lot of opportunity as well that is going to flood this industry in the years to come.  

Because of the increasing size of this market we are no longer in the age of “There’s an app for that” and we’re leaving behind our dreams of writing a single app that will make us millionaires.  Already it’s at the point where just writing an application isn’t going to be enough.  It needs to deliver an unprecedented experience to the user and engage them like never before.  While I don’t have all of the answers on how to build an app that will make you enough money to buy a yacht and date a model, I do have a few pointers on how not to get left behind, and maybe, if you work hard, make a few kick ass apps in the process.

###Our Not So Simple Application

I’m assuming that some of you already have experience with Android fundamentals so we’ll jump right into some code and start looking at some apps.  The first one I’m going to show you is using the Android SDK and nothing else.  The out of the box development tools that Google provides you gives you everything you need to develop, test, and debug your applications.  As most of you know, that while a lot is given to you there is usually additional work that needs to done, outside of the SDK, to solidify your application’s architecture and give your app style and personality.  

That is why I am going to show you a second version of the same app but this one is going to be leveraging a few third party libraries.  We’ll look at how the code has been simplified and I’ll let you decide which which looks better.

Our app is going to make an asynchronous call request to Marvel's API Gateway (yes, it's a superhero app...deal with it) and 
display the results in a gridview with each item containing an image and some associative text.  Clicking on one of the adapter items 
is going to launch a new activity that makes a second query to get the detailed information for our hero and it's shown to the user 
with a large image, the hero's name, and a brief description.

####Let's take a look at our first app

You can see that our heros are displayed in a staggered grid view.  When I scroll the image down quickly you can see that there is a grey box 
and the image is "popped" in there and it doesn't look good.  This is because I am not caching the images (storing them locally on the 
device) it would be too much work as time was limited.  Clicking on one of the heroes we are taken to a new activity with a larger image, a bland title, 
and a description.

This is what the first version of the app looks like under the hood…

Explanation of the UML. 
Briefly explain the code for: 
The AsyncTask and it’s HTTP Stream and API Items. 
Image Downloader and it’s Async Task

What is an API?  Why is data commonly consumed in this form for mobile applications?  
https://www.youtube.com/watch?v=s7wmiS2mSXY 
https://www.copterlabs.com/json-what-it-is-how-it-works-how-to-use-it/ 
https://developer.marvel.com 

API stand for Application Programming Interface.

It’s messenger that takes requests and tells the system what you want to do and then returns the response back.  Think of a waiter (the API) in a restaurant.  Sending your order (request) to the kitchen and bringing you back your meal (response). 

###Leveraging 3rd Party Libraries

As you get more experience with the Android platform you discover a large community of developers that contribute to third party and open source libraries.  This enables you from having to “reinvent the wheel”.  Some of these libraries are developed by large companies and assist you with the overall structure of your project, think Square and Robolectric, while others are just fun to have in your application like a KenBurnsView..you’ll get to see it in a second.

As a developer you need to be able to understand what the pros and cons are of any library that you decide to use in your project.  In order to do this you need to at least have an idea of how they work under the hood and how large they are.  Android has a “multi-dex limit” that limits the number of methods that you are allowed to have in your project to 64,000.  If you go above that it drastically increases your app’s APK size increasing its download time and taking up more of the phone memory.  

I’m going to show you how to simplify HTTP requests using Retrofit 2 and how to easily download an image and transform it into a bitmap without the need of an http stream or bitmap factory using Glide.  

Retrofit 2 
https://github.com/square/retrofit 

Let’s start with Retrofit 2.  This is one of the libraries that assists with the structure of your project.  It is widely used and is supported by Square (and Jake Wharton, omg).  Retrofit is a type-safe HTTP client for Android.  It enables you to use annotations to describe HTTP requests as well as URL and query parameters.  It also provides functionality for multi-part requests, file uploads, and error handling.

There are only a few pieces that you need to put into place to get Retrofit up and running in your application.

1) First you need to is incorporate dependencies.  I am using Gradle (because I’ve never worked with Maven, honestly).  When you include these in your app level build.gradle file Gradle downloads the appropriate .jar files for use in your application.

2) The first component of Retrofit that we’re going to look at is the closest to our API.  We write a Retrofit Client which acts as an endpoint for our Retrofit Service, meaning it builds the API query for us and gives us the response in the form of a Callback typed as our expected result.  It also allows for dynamic parameter replacement.  Retrofit adapts a Java interface to HTTP calls by using annotations on the declared methods to define how requests are made.

Instead of writing the full query below we can use the Retrofit:
https://gateway.marvel.com/v1/public/characters?orderBy=-modified&limit=20&ts=1477354443291&apikey=4b08badbb0599b9173d3d2d1fb8d0727&hash=5df8b97b8bb68186741fdc0a80de50fe 

3)  Next we need the central component of our Retrofit implementation.  The actual Retrofit object and the Service that we are going to build around it.  We create an instance of Retrofit using its builder and generate our HTTP call by dictating the HTTP Client (Factory for calls, which can be used to send HTTP requests and read their responses) and a Converter we want to use.  

We are going to be using a slight variation of the OkHttpClient that is provided to us by Retrofit.  The one I am using just provides additional logging of responses.  But there are more out there that allow you to manipulate ports, packages, and authentication procedures.  As for the converter we are going to use the GSONConverterFactory, Gson is a library used for converting JSON object to POJOs and vice-versa.

One of the huge upsides of using Retrofit for me is the way it automatically maps objects form JSON to POJOs.  You saw in the last project I had to write code that navigated the JSON structure and I had to map the JSON objects myself.  While not a difficult task it requires a large amount of boilerplate code and is no fun.

In order to actually make the query to our API using Retrofit we need to create our Service, passing in the class we want to construct.  Use our client to build the proper query and then handle the call’s response asynchronously.

Glide.  Show code before and after.
https://github.com/bumptech/glide

Glide is an open source media and image management library that is accessed via interface.  It encompasses downloads, caching, and resource pooling.  If you notice on the simple project there is a grey square whenever the layout manager notices that a new card needs to be constructed, the adapter creates the new ViewHolder, and the image is re-downloaded.  This is because I was not saving the images to memory as they were downloaded, a tasks that would take me a little longer than I would like.  Glide automatically stores these images for you.

Pallette
https://github.com/florent37/GlidePalette 
https://www.bignerdranch.com/blog/extracting-colors-to-a-palette-with-android-lollipop/

These colors that are accentuating the image. Palette is an API for Android that allows you to extract and make use of colors in an image.  While Palette itself isn’t an open source library I put it in this project to show that you can use external libraries to make existing components of your app even more powerful.  Such as using Palette within your Guid callback.

###Contributing to Open Source Projects

Looking at our application before and after it’s pretty easy to see the power and usefulness of open source libraries provided by the Android community.  They allow you to save development time, improve the readability of your code, and sometimes just make your app look a little cooler.  Developers working on small projects as well as large companies are adopting the practice of open sourcing their software as well as the libraries and tools that support them. This year Bulgaria passed a law requiring all software written for the government be open source and to be developed as such in a public repository.  It’s great that Bulgaria has done this because it will provide software solutions for its country’s coders, improve the quality of the software being developed, and increase government transparency.

Why is it becoming more prevalent and popular?

There’s no reason not to.  The cost of maintaining an open source library is low compared to what can be gained.
Having your code accessible to the public means that there will be more eyes looking at your code, pointing bugs and other things that you might have missed.  Some of these eyes might even belong to a developer who will take care of the problem for you and perhaps extend your project as well…for free!
It improves developer morale.  Developers love to give back to the community and collaborate with other developers.
It also improves the company’s reputation.  It is very quickly becoming “cool” to make your projects available to the public.

Here’s an example showing the workflow of contributing. HabaticaRPG.

For Ouli, our consumer mobile application that recommends activities in the Washington DC area, we are using a few of these libraries you’ve just seen and a few more.  These include Dagger 2 a library that allows for dependency injection throughout your application, implementing a singleton of your objects.  Using this library allows for increased modularity in your projects and helps support a healthy application architecture.  We use libraries such as Robolectric and Mockito to provide the support we need for unit and integration testing.  As well as a few others that help make development easier day to day.  Dante is also embracing the open source lifestyle.  We have started putting several web applications using Serverless on Github and as our company grows and we continue to grow there will be new projects that we release to the public.   

###Conclusion

To conclude, we’ve learned that the use of mobile technology is on the rise all over the world.  As the number of people with smartphones rises so will data consumption and app revenue.  In order to not only thrive but to survive in this space as it is becoming more competitive you need to be able to see the benefits in tools and resources and leverage them to give yourself a leg up.  There is no need to reinvent the wheel.

We took a look at a few third party libraries, all of which have been provided by the Android community which is growing at the same pace as mobile phones are being sold.  Meaning there is bound to be some exciting stuff down the line.  Now that you know how to benefit from these resources and how to contribute of Android’s community maybe the next big open source library will come from you.

Thank you.
