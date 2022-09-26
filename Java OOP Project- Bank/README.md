# PAO-Project

Project that simulates a bank, with the following entities and their respective services:

PERSON: name, UID (CNP, SSN or other unique identity number), address, person_id, number_of_people(a counter for the number of people), a list of all accounts that the person has, and the same for the past statements and the past transactions.

ACCOUNT: inherits PERSON, also has: balance, opening_date, number_of_accounts(a counter for the number of accounts open), id and bank, that contains the name of the bank.

DEBIT: inherits ACCOUNT, also has: account_type, that contains the type of account, which grants certain fees.

SAVINGS: inherits ACCOUNT, also has: period(length of the time period of the savings account), interest_rate, balance_history, which has the history of the balance in the savings account.

CARD: card_number, which is randomly generated, used_numbers(a list of the used card numbers), account_ids(a list of the accounts that have cards), the PERSON that owns the card, the ACCOUNT that the card is issued for.

STATEMENT: account, debit, savings, where the account is saved, statement(the statement is saved in this variable), id, number_of_statements.

TRANSACTION: account, debit, savings, transaction(where the transaction text is saved), sum, date, id, number_of_transactions.

USER: user_details, which is the person that the user is created for, username, password, role, number_of_users, user_id.

SERVICES: all entities have functions for: getting all entities added to service, get an entity by id, update entity, add entity, delete entity, delete all entities. Specific service functions are the following:

PERSON SERVICE: AddAccount - adds an account to the PERSON account list, AddStatement - adds statement to the PERSON statement list, AddTransaction - adds transaction to the transaction list

ACCOUNT, DEBIT, SAVINGS SERVICE: doStatement - prints a statement and adds it to the PERSON list of statements, AddFunds - adds funds to the account, WithdrawFunds - withdraws funds from the account, SendFunds - sends funds from an account to another.
