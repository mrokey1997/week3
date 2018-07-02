# Project 3 - Mini Twitter

Mini Twitter is a [twitter client](https://apps.twitter.com/) which displays the home timeline and can compose a tweet.

Time spent: 40 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can sign in to Twitter using OAuth login
* [x] User can view the tweets from their home timeline
	* [x] User should be displayed the username, name, and body for each tweet
	* [x] User should be displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
	* [x] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView)
* [x] User can compose a new tweet
	* [ ] User can click a “Compose” icon in the Action Bar on the top right
	* [x] User can then enter a new tweet and post this to twitter
	* [x] User is taken back to home timeline with new tweet visible in timeline

The following **optional** features are implemented:
* [x] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
* [x] Links in tweets are clickable and will launch the web browser (see [autolink](http://guides.codepath.com/android/Working-with-the-TextView#autolinking-urls))
* [x] User can refresh tweets timeline by [pulling down to refresh](http://guides.codepath.com/android/Implementing-Pull-to-Refresh-Guide) (i.e pull-to-refresh)
* [ ] User can open the twitter app offline and see last loaded tweets
	- Tweets are [persisted into sqlite](http://guides.codepath.com/android/ActiveAndroid-Guide) and can be displayed from the local DB
* [ ] User can tap a tweet to display a "detailed" view of that tweet 
* [ ] User can select "reply" from detail view to respond to a tweet
* [x] Improve the user interface and theme the app to feel "twitter branded"
* [x] Stretch: User can see embedded image media within the tweet detail view
* [ ] Stretch: User can watch embedded video within the tweet
* [x] Stretch: Compose activity is replaced with a [modal overlay](http://guides.codepath.com/android/Using-DialogFragment)
* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler)
* [ ] Stretch: Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate
* [x] Stretch: [Leverage RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) as a replacement for the ListView and ArrayAdapter for all lists of tweets
* [ ] Stretch: Move the "Compose" action to a [FloatingActionButton](https://guides.codepath.com/android/Floating-Action-Buttons) instead of on the AppBar
* [ ] Stretch: Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate
* [ ] Stretch: Leverage the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into one or more activity or fragment layout templates
* [x] Stretch: Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering

The following **additional** features are implemented:

* [x] Addition many animation
* [x] User can click on avatar to view detail profile of other users
* [x] User can view their detail profile 

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/eWGN2uM.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
<img src='https://i.imgur.com/5xfzdOa.gif' title='My Profile' width='' alt='My Profile' />
<img src='https://i.imgur.com/zIpHRqT.gif' title='User Profile' width='' alt='User Profile' />
<img src='https://i.imgur.com/3I7h89z.gif' title='Compose' width='' alt='Compose' />


## Notes

## Open-source libraries used

- [Retrofit](http://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
- [Glide](https://github.com/bumptech/glide) - A fast and efficient image loading library for Android

## License

    Copyright 2018 Vo Xuan Bach

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.