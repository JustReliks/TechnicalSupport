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
- Для удобства тестирования пароль отправляется в оригинальном его виде при логине. Правильнее было бы сделать прием пароля в зашифрованном виде.
- Запускать приложение нужно при запущенном докере. При этом вручную создавать базу данных не нужно, она создастся в докере.
- Сервисы (получение запросов) нужно сделать как-то красивее, но переделать не успеваю.

Созданные пользователи (логин:пароль):
- Admin:Admin
- Operator:Operator
- User:User
- Operator_Admin:Operator_Admin