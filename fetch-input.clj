#!/usr/bin/env bb
(require '[babashka.curl :as curl])
(require '[clojure.tools.cli :refer [parse-opts]])

(def cli-options
  [["-y" "--year YEAR" "Year to fetch input from"
    :default 2022
    :parse-fn #(Integer/parseInt %)
    :validate [#(<= 2015 % 2022) "Must be a valid year of AOC"]]
   ["-d" "--day DAY" "Day to fetch input from"
    :default 1
    :parse-fn #(Integer/parseInt %)
    :validate [#(<= 1 % 25) "Must be a valid day of AOC"]]
   ["-o" "--output OUTPUT" "File output path"
    :default "input.txt"]
   ["-h" "--help"]])

(def cookie "TODO: COOKIE HERE PUT IN CONFIG FILE EVENTUALLY")

(let [{:keys [options summary]} (parse-opts *command-line-args* cli-options)
      {:keys [year day output help]} options]
  (when help
    (println summary)
    (System/exit 0))
  (->> (curl/get (str "https://adventofcode.com/" year "/day/" day "/input") {:headers {"Cookie" cookie}})
       :body
       (spit output)))
