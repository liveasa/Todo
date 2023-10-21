import express, { Express } from 'express';
import { MockController } from './controller/mock-controller';
import bodyParser from 'body-parser';

const app: Express = express();
const port = 3000;
const ctrl = new MockController();
const jsonParser = bodyParser.json();

app.get('/todos', ctrl.listTodos);
app.post('/todos/add', jsonParser, ctrl.createTodos);
app.put('/todos/:id', jsonParser, ctrl.updateTodos);
app.delete('/todos/:id', ctrl.deteteTodos);

app.listen(port, () => {
    console.log(`⚡️[server]: Server is running at http://localhost:${port}`);
});