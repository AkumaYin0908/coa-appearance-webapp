"use strict";

import { baseUrl } from "./modules/base-url.js";
import { displayCurrentLeader } from "./modules/html-content.js";
import { appearanceHistoryTableObject } from "./modules/table-object.js";

const dataTable = await $("#appearance").DataTable(appearanceHistoryTableObject(`${baseUrl}/appearances/DESC`));
