**База данных**:

request:
- id
- name
- phone_number
- message
- status_id (default draft)
- created_at (default now)
- created_by

user:
- id
- username
- password

role:
- id
- name
  values: (User, Operator, Admin)

user_role:
- id
- user_id (fk)
- role_id (fk)

status:
- id
- name
  values: (Draft, Sent, Accepted, Rejected)


Замечания:
- Пароль в эндопойт логина нужно передавать в зашифрованном виде Base64.