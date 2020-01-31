/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import MainMvxView from './MainMvxView';
import ExpensesList from './ExpensesList';

AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerComponent('MainMvxView', () => MainMvxView);
AppRegistry.registerComponent('ExpensesList', () => ExpensesList);
