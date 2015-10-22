# changelog-maven-plugin
This is a maven based plugin that generates a changelog based on the git commits.

## Git Commits

### Format
Commits must be commited in a specific format for the generator to add them unto the changelog. 

```
<type>(<scope>):<subject>

<body>

<footer>
```

### Fields

``<type>`` In this field you add the type of commit that you're making.

- **feat** for Features
- **fix** for Bug Fixes
- **docs** for Documentation
- **style** for Style
- **refactor** for Refactoring 
- **test** for Tests
- **chore** for Chores

``<scope>`` In this field you enter the current scope of the commit.

``<subject>`` In this field you enter the subject of the commit.

``<body>`` In this field you enter the body of the commit.

``<footer>`` In this field you enter the footer of the commit.

Example commit:

```
feat(rest-api):Added new services to the rest-api

The createClient and the deleteCustomer services were added

This closes issue #1442
```

## Versions

### Format
Versions must be commited in a specific format for the generator to add them unto the changelog. 

```
version:<versionName>
```
### Fields

``<versionName>`` In this field you add the name of the version you want to create.
