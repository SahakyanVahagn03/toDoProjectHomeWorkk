window.onload = function () {
    bootlint.showLintReportForCurrentDocument([], {
        hasProblems: false,
        problemFree: false
    });

    $('[data-toggle="tooltip"]').tooltip();

    function formatDate(date) {
        return (
            date.getDate() +
            "/" +
            (date.getMonth() + 1) +
            "/" +
            date.getFullYear()
        );
    }
}




//change to do

function TodoMVC() {
    const storage = Storage()
    const { todos } = storage.get()

    return {
        init() {
            // needed because #/ navigation breaks [autofocus]
            this.$refs.newTodoInput.focus()

            const router = Router({
                '/all': () => {
                    this.filter = 'all'
                },
                '/active': () => {
                    this.filter = 'active'
                },
                '/completed': () => {
                    this.filter = 'completed'
                },
            })

            router.init('/all')
        },

        save() {
            this.$nextTick(() => {
                storage.set({ todos: this.todos })
            })
        },

        // data

        todos,
        newTodo: '',
        filter: 'all',
        nav: [
            { name: 'all', title: 'All' },
            { name: 'active', title: 'Active' },
            { name: 'completed', title: 'Completed' },
        ],

        // filtering

        listTodos() {
            switch (this.filter) {
                case 'active':
                    return this.todos.filter(isNotCompleted)
                case 'completed':
                    return this.todos.filter(isCompleted)
                default:
                    return this.todos
            }
        },

        // toggle compeleted check

        toggleAll({ target: { checked } }) {
            this.todos.forEach(todo => {
                todo.completed = checked
            })
        },

        toggle(todo, { target: { checked } }) {
            todo.completed = checked
        },

        countIncomplete() {
            return this.todos.reduce(
                (acc, { completed }) => acc + Number(!completed),
                0,
            )
        },

        // adding new todos

        add() {
            const id = new Date().getTime()
            this.todos = [
                ...this.todos,
                { id, title: this.newTodo.trim(), completed: false },
            ]
            this.newTodo = ''
        },

        // removing todos

        remove(todo) {
            const idx = findIdx(todo, this.todos)
            this.todos = [
                ...this.todos.slice(0, idx),
                ...this.todos.slice(idx + 1, this.todos.length),
            ]
        },

        clear() {
            this.todos = this.todos.filter(isNotCompleted)
        },

        // editing todos

        editing(todo) {
            todo.editing = true
            todo.edit = todo.title

            this.$nextTick(() => {
                document.getElementById(`edit-${todo.id}`).focus()
            })
        },

        edit(todo) {
            const newTitle = todo.edit.trim()
            if (!newTitle) {
                this.remove(todo)
            } else {
                todo.title = newTitle
                this.reset(todo)
            }
        },

        reset(todo) {
            todo.editing = false
            todo.edit = ''
        },

        // counter at the bottom

        showCounter() {
            const count = this.countIncomplete()
            return `<strong>${count}</strong> item${count === 1 ? '' : 's'} left`
        },
    }
}

function Storage() {
    const KEY = 'todos-alpinejs'
    const defaultData = '{ "todos": [] }'

    return {
        get() {
            return JSON.parse(localStorage.getItem(KEY) || defaultData)
        },
        set({ todos, ...rest }) {
            localStorage.setItem(
                KEY,
                JSON.stringify({
                    ...rest,
                    todos: todos.map(({ id, title, completed }) => ({
                        id,
                        title,
                        completed,
                    })),
                }),
            )
        },
    }
}

function findIdx(todo, todos) {
    const idx = todos.findIndex(({ id }) => id === todo.id)
    if (idx < 0) throw Error(`Can not find todo`)
    return idx
}

function isCompleted({ completed }) {
    return completed
}

function isNotCompleted({ completed }) {
    return !completed
}


