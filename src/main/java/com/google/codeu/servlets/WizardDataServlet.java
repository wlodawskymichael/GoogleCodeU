/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Get data to initialize the map. */
@WebServlet("/map-data")
public class WizardDataServlet extends HttpServlet {
  /** Private class to be used to cast the data to an Object. */
  private class WizardSighting {
    private String state;
    private double lat;
    private double lng;

    private WizardSighting(String state, double lat, double lng) {
      this.state = state;
      this.lat = lat;
      this.lng = lng;
    }
  }

  private JsonArray wizardSightingsArray;

  /**
   * Initialize the data by reading the user-invisible CSV file and convert the data to a json
   * structure.
   */
  @Override
  public void init() {
    wizardSightingsArray = new JsonArray();
    Gson gson = new Gson();
    Scanner read =
        new Scanner(getServletContext().getResourceAsStream("/WEB-INF/wizard_reports.csv"));
    while (read.hasNextLine()) {
      String line = read.nextLine();
      String[] dataPoint = line.split(",");
      // Make sure we are only dealing with valid data
      if (dataPoint.length == 3) {
        String state = dataPoint[0];
        double latitude = Double.parseDouble(dataPoint[1]);
        double longitude = Double.parseDouble(dataPoint[2]);

        wizardSightingsArray.add(gson.toJsonTree(new WizardSighting(state, latitude, longitude)));
      }
    }
    read.close();
  }

  /** Get the data we initialized for front-end use. */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.setContentType("application/json");
    res.getOutputStream().println(wizardSightingsArray.toString());
  }
}
