# WebGranny
Library to test web applications. This just wraps another test framework like Selenuim or HTTPUnit, but supplies an API focused on user interaction instead of on technical details of the respective tested application.

Test sources itself should look like you are explaining your granny what to do, so instead of "select the &lt;a&gt; element of style class xy inside the &lt;div&gt; with id xyz" it should be possible to state "below header Actions click on perform".

- better testing of user experience
- tests are more stable when implementation details of application change
- allows test first approach