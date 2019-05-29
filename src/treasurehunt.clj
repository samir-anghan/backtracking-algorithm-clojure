(ns treasurehunt)
(use 'clojure.java.io)

(def noOfRows)
(def noOfColumns)
(def charactersVec [])
(def mazeArray)
(def index 0)
(def goalX)
(def goalY)
(def goalCharacter "@")
(def x 0)
(def y 0)
(def goalFound false)
(def charsInLine 0)
(def isValidMap true)

(defn CountRowsFn []
      (with-open [rdr (reader "map.txt")]
        (def noOfRows (count (line-seq rdr)))))

(defn CountColumnsFn []
      (with-open [rdr (reader "map.txt")]
        (doseq [line (line-seq rdr)]
          (def noOfColumns (count line)))))

(defn SetCharactersVectorFn []
      (with-open [rdr (reader "map.txt")]
        (doseq [line (line-seq rdr)]
          (doseq [character line]
            (def charactersVec (conj charactersVec character))))))

(defn ValidateMapFn []
      (with-open [rdr (reader "map.txt")]
        (doseq [line (line-seq rdr)]
          (def charsInLine (count line))
            (if (not= charsInLine noOfColumns)
              (def isValidMap false)))))

(defn MapToArrayFn []
      (def mazeArray (make-array Character/TYPE noOfRows noOfColumns))
      (doseq [x (range noOfRows)
              y (range noOfColumns)]
        (let [character (get charactersVec index)]
          (aset mazeArray x y character))
        (def index (+ index 1))))

(defn GetGoalCoordinatesFn []
      (doseq [x (range noOfRows)
              y (range noOfColumns)]
        (let [character (aget mazeArray x y)]
          (if (= (str character) goalCharacter)
            (do
              (def goalX x)
              (def goalY y))))))

(defn isSafeFn [maze x y]
      (if (and (< x noOfRows) (>= x 0) (>= y 0) (< y noOfColumns))
        (do
          (let [character (aget maze x y)]
            (if (= (str character) "-")
              true)))))

(defn PrintMazeFn []
      (println "")
      (doseq [x (range noOfRows)
              y (range noOfColumns)]
        (print (aget mazeArray x y))

        (if (= y (- noOfColumns 1))
          (println "")))
      (println ""))

(defn SolveMazeUtilFn [maze i j]

      ;if (x, y is goal) return true
      (if (and (= i goalX) (= j goalY))
        (do
          (println "Woo hoo, I found the treasure :-)")
          (def goalFound true)
          (PrintMazeFn)
          (System/exit 0)))

      ;Move forward in each direction and recursively check if this move leads to a solution
      (if (= true (isSafeFn maze i j))
        (do
          (aset maze i j \+)
          (SolveMazeUtilFn maze (+ i 1) j)
          (SolveMazeUtilFn maze i (+ j 1))
          (SolveMazeUtilFn maze (- i 1) j)
          (SolveMazeUtilFn maze i (- j 1))
          (aset maze i j \!)
          false
          )))

(defn MainFn []
      (CountRowsFn)
      (CountColumnsFn)
      (SetCharactersVectorFn)
      (ValidateMapFn)
      
      (if (= true isValidMap)
        (do
          (MapToArrayFn)
          (GetGoalCoordinatesFn))
        (do
          (println "@")
          (println "@ FEEDBACK: Does not appear to be a valid map file !!! Try again with a different map file.")
          (println "@")
          (System/exit 0)))

      (println "This is my challenge:")
      (PrintMazeFn)

      (SolveMazeUtilFn mazeArray x y)

      (if (= false goalFound)
        (do
          (println "Uh oh, I could not find the treasure :-(")
          (PrintMazeFn))))

(MainFn)

