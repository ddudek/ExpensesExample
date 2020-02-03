# Expenses Example

Example project to test MVP approach with interchangeble pure Android view and React native implementations
Business logic and networking implemented in Android.

What was the goal of this approach:
- Exchangable view implementation (android / react-native) without changing business logic
- Views are completely passive
- All layers should be unit testable

Added two unit test classes for test examples:
GetFilteredExpenseListUseCaseTest.kt
ExpenseDetailsPresenterTest.kt

## Libraries used:
- Glide - to load images
- Retrofit - for networking
- RxJava - for networking wrapper and use cases
- Dagger2 - dependency injection

## Notes:
- Only android project was implemented, iOS is not in scope of this example

## TODO:
* use Dagger2 for dependency injection (should be straight-forward)
