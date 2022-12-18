# Sportsbook
| List | Favorites |
| --- | --- |
| <img src="https://user-images.githubusercontent.com/15250492/208312719-4007da47-fc7f-42e9-bbc7-aaf448a04dce.png"  height="500" /> | <img src="https://user-images.githubusercontent.com/15250492/208312723-22b7846d-476e-4fed-b19e-90cf1f48b067.png"  height="500" /> |

## Technologies
- Clean Architecture
- Single Activity Application
- Navigation Components
- MVVM/MVI Hybrid
- Material UI
- Kotlin
	- Coroutines
	- Flows 
- Dependency Injection **(Hilt)**
- Http Client **(Retrofit**)
- Local Database **(Room)**
- Unit  & UI Testing
	- JUnit4
	- Espresso
	- Mockk
	- Turbine
	- AssertJ
	- Truth

## Project Structure
***Modularization by feature and layer.***
![deps_graph](https://user-images.githubusercontent.com/15250492/208312866-e0c1f711-8218-42e5-b4c9-8f78a05244c0.png)


*Graph generated using the custom gradle task `projectDependencyGraph` that can be found in `root/config/tasks/custom-tasks.gradle`*

**App** module

> App module is the only one that knows all the feature modules and it links feature modules together. Depends on other features and libraries. It contains the single host activity. It is responsible for providing the complete graph of the app, adding the NavHost to the UI and orchestrates navigation, since features don’t know about eachother.

**Feature** modules

> *All of these Feature Modules are user-facing functionality. Must notdepend on other features or app, but it can depend on several librarymodules Each module should contain its own presentation (MVVMI), domain, data & data sources layers (if they require it).*
> > **[List] :**
> > *Containes sports' list feature implementation.*
> 
>> **[Favorites] :**
> > *Contains favorites feature implementation*

**Library** modules

> *Implementation that is reused across serveral or all feature or app. Can depend on other libraries.*
> 
>> **[Api] :**
> > *Contains the design system.*
> 
>> **[Common] :**
> > *Contains common classes and resources that are used from multiple features and libraries.*
> 
> > **[Database] :**
> > *Contains the database (Room) base implementation.*
> 
> > **[MVVMI] :**
> > *Contains the MVVM/MVI hybrid base implementation.*
> 
> > **[Navigation] :**
> > *Contains the app navigation implementation.*
> 
> > **[Test] :**
> > *Contains the base unit and ui test implementation and utils, also provides the dependencies as API dependencies.*
> 
> > **[UI] :**
> > *Contains the design system.*



