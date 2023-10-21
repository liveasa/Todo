import assign from 'lodash/assign';
import { Request, Response } from 'express';

interface Task {
    id?: string,
    todo?: string,
    completed?: boolean
}

export class MockController {

    // holds list of todo items
    tasks: Array<Task> = [];

    // lists todos
    listTodos = (req: Request, res: Response) => {
        res.json(this.tasks)
    }

    // create todo
    createTodos = (req: Request, res: Response) => {
        this.tasks.push(req.body)
        res.json(this.tasks)
    }

    // update todo
    updateTodos = (req: Request, res: Response) => {
        let todoIndex = this.tasks.findIndex((t) => { return t.id == req.params.id })
        if (todoIndex != -1) {
            this.tasks[todoIndex] = assign(this.tasks[todoIndex], req.body);
        }
        res.json(this.tasks)
    }

    // delete todo
    deteteTodos = (req: Request, res: Response) => {
        this.tasks = this.tasks.filter((t) => { return t.id != req.params.id })
        res.json(this.tasks)
    }

}