API Endpoints:
| **Method** | **Endpoint** | **Description** | **Request Parameters / Body** | **Expected Response** |
|-----------|------------|----------------|-----------------------------|----------------------|
| **GET** | `/api/search?keyword={keyword}` | Universal search for doctors and practices | `keyword` (query param) | List of matching doctors and practices |
| **GET** | `/api/practices/{id}` | Get practice details by ID | `id` (path variable) | Practice details (JSON) |
| **POST** | `/api/practices` | Create a new practice | `{ "name": "Sunrise Clinic", "city": "Los Angeles", "state": "CA" }` | `201 Created` with practice details |
| **PUT** | `/api/practices/{id}` | Update an existing practice | `id` (path variable) + Updated details | `200 OK` with updated practice details |
| **DELETE** | `/api/practices/{id}` | Delete a practice by ID | `id` (path variable) | `204 No Content` (if successful) |
| **GET** | `/api/doctors/{id}` | Get doctor details by ID | `id` (path variable) | Doctor details (JSON) |
| **POST** | `/api/doctors` | Create a new doctor | `{ "name": "Dr. John Doe", "experience": "10 years", "specialities": ["Cardiology"] }` | `201 Created` with doctor details |
| **PUT** | `/api/doctors/{id}` | Update an existing doctor | `id` (path variable) + Updated details | `200 OK` with updated doctor details |
| **DELETE** | `/api/doctors/{id}` | Delete a doctor by ID | `id` (path variable) | `204 No Content` (if successful) |
