# Access Control Service (ACS)
Access control microservice that provides universal resource access and rights management.
Supports unlimited amount of resource types and scopes, suitable for projects of any scale.

| Method | Endpoint                          | Description                                 |
|--------|-----------------------------------|---------------------------------------------|
| GET    | /                                 | Healthcheck                                 |
| POST   | /definitions/refresh              | Refresh definitions from source             |
| GET    | /scopes                           | List loaded scopes                          |
| GET    | /scope/{scope}                    | Get scope details                           |
| GET    | /scope/{scope}/nodes              | Get nodes for scope                         |
| GET    | /scope/{scope}/tree               | Get scope node tree                         |
| GET    | /scope/{scope}/subject/{resource} | Get subject details                         |
| DELETE | /scope/{scope}/subject/{resource} | Delete subject                              |
| POST   | /subject/create                   | Create subject                              |
| POST   | /rights/grant                     | Grant rights                                |
| POST   | /rights/revoke                    | Revoke rights                               |
| POST   | /rights/check                     | Check scoped rights                         |
| POST   | /rights/lookup                    | List granted nodes for subject on a subject |