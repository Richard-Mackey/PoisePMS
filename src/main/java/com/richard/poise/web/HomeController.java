package com.richard.poise.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Controller for application home/root URL. Redirects root path to the main projects page. */
@Controller
public class HomeController {
  /**
   * Handles requests to the root URL.
   *
   * @return redirect to projects list page
   */
  @GetMapping("/")
  public String home() {
    return "redirect:/projects";
  }
}
