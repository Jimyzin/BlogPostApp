# BlogPostApp

## Problem Statement
You have been tasked with to build a blog post API.

The API should facilitate create, update, delete of a single blog post. It can also fetch all blog posts.

#### Requirements

1. Using JAVA, Springboot REST API’s.
2. You may make necessary assumptions if time is a constraint.
3. Your code should be production ready.
4. Bonus question - Consider security approaches for your API. It can be documented rather than implemented, if time is a constraint.
5. You may use git and send us the link.
6. For database, you can use any in-memory  (like H2) database if that make it easy.

## Solution
The solution is implemented in  ``SpringBoot`` that exposes REST APIs. A user needs to ``authenticate`` and must have proper ``authorisations`` to access the REST APIs. The input objects are validated before processing.

### REST APIs

The application is deployed at https://sujoykb-blog-post-app.herokuapp.com/

Http Method | REST Endpoints                                   | Purpose                               | Roles Permitted | Heroku Endpoint                                                  | 
-------------  |--------------------------------------------------|---------------------------------------|-----------------|------------------------------------------------------------------|
POST  | /blog/create                                     | Creates a new blog                    | USER and ADMIN  | https://sujoykb-blog-post-app.herokuapp.com/blog/create          |
PATCH| /blog/{postId}/update                            | Updates a blog post owned by the user | USER and ADMIN  | https://sujoykb-blog-post-app.herokuapp.com/blog/{postId}/update |
GET | /blog/viewAll                                     | Shows all blog posts of all users                | USER and ADMIN    | https://sujoykb-blog-post-app.herokuapp.com/blog/viewAll         | 
GET| /blog/{postId}/view                              | Shows a specific blog post belonging to any user | USER and ADMIN                        | https://sujoykb-blog-post-app.herokuapp.com/blog/{postId}/view 
GET| /blog/myViewAll                                  | Shows all blog posts owned by the user           | USER and ADMIN                        | https://sujoykb-blog-post-app.herokuapp.com/blog/myViewAll       | 
DELETE| /blog/{postId}/delete                            | Deletes a specific blog post owned by the user   | USER and ADMIN                        | https://sujoykb-blog-post-app.herokuapp.com/blog/{postId}/delete |
DELETE| /blog/{postId}/adminDelete                       | Deletes a specific blog post owned by any user   | ADMIN                                 | https://sujoykb-blog-post-app.herokuapp.com/blog/adminDelete     |

```
The application uses Basic Authentication. The following are pre-existing users for testing.
   username: user2                username: admin
   password: pass2                password: admin
   role: ROLE_USER                role: ROLE_ADMIN
```

### Assumptions
1. A blog comprises of 2 parts - title and content.
2. ``Embedded H2`` has been used as the datasource for this application.
3. Authorisation roles supported in the application are ``ROLE_ADMIN`` and ``ROLE_USER``.
4. The users - ``admin`` and ``user2`` are populated in the database during the bootup from ``/resources/data.sql``.
5. Users have many-to-many relationship with authorisation roles.
6. A user may have multiple blog posts.
7. All users can see all blogs - their own ones as well as the ones created by others.
8. Each user can also choose to see just the blogs owned by him/her.
9. A user can strictly modify or delete the blogs owned by him/her.
10. Admin has the only exclusive authorisation to delete a blog created by another user, one blog at a time.
11. For the sake of simplicity, there is no API to add new users or admins or new authorisation roles.
12. There is no swagger-ui.

### Implementation
1. ``Embedded H2`` holds 3 tables - ``User, Authority and Post``. User has many-to-many relationship with Authority as one user may have multiple authorisation roles as well as the same authorisation role may be applied to more than one user. User has one-to-many relationship with post as one user can have multiple blogs.
2. ``JPA`` is used as the ORM tool.
3. The functionalities supported are blog creation, listing, a specific blog view, modify and delete.
4. These functionalities are exposed to the outside world as ``REST APIs``.
5. The REST APIs are secured by ``Spring Security``. This means a user must use proper credentials to authenticate to call the REST APIs.
6. A user must have proper authorisations defined by their roles in order to call REST APIs. The user and authorisation details are held in H2 datasource.
7. Spring Security has been implemented by extending ``UserDetailsService`` and ``UserDetails`` interfaces.
8. ``Method-level`` authorisations have also been implemented to grant access based on user roles.
9. The passwords are encrypted and then stored in the datasource.
10. ``Bean Validation`` is performed on the input parameters including designing a ``cuatom bean validation and annotation`` for ``PostupdateRequest``
11. The service and controller layers are covered with Junit5 test cases.
12. Performance Optimisations have been placed - The ``User`` object fetched from database (and embedded in ``Principal`` object) at the time of authentication are reused in the service layer without fetching again from the database.
13. BlogPostApp is deployed in Heroku.
14. Manual test results are placed in ``/postman_test_results``.

