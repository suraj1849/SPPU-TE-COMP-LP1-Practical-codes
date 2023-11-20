import tkinter as tk

# STICKY NOTES
import tkinter as tk
from tkinter import messagebox, simpledialog

def save_note():
    note_text = text_entry.get("1.0", "end-1c")
    if note_text.strip():
        with open("notes.txt", "a") as file:
            file.write(note_text + "\n")
        text_entry.delete("1.0", tk.END)
        messagebox.showinfo("Note Saved", "Your note has been saved.")
    else:
        messagebox.showwarning("Empty Note", "Please enter some text before saving.")

def view_notes():
    try:
        with open("notes.txt", "r") as file:
            notes = file.read()
        notes_window = tk.Toplevel(root)
        notes_window.title("Notes")
        notes_text = tk.Text(notes_window, bg="lightyellow", fg="blue")
        notes_text.insert(tk.END, notes)
        notes_text.pack()
    except FileNotFoundError:
        messagebox.showwarning("No Notes Found", "There are no notes to display.")

def clear_notes():
    confirmed = messagebox.askyesno("Confirm", "Are you sure you want to clear all notes?")
    if confirmed:
        with open("notes.txt", "w") as file:
            file.truncate(0)
        messagebox.showinfo("Notes Cleared", "All notes have been cleared.")

def open_specific_note():
    try:
        with open("notes.txt", "r") as file:
            notes = file.readlines()
        
        note_index = simpledialog.askinteger("Open Note", "Enter the note number you want to open:", minvalue=1, maxvalue=len(notes))
        
        if note_index:
            note_index -= 1  # Adjust to 0-based index
            note = notes[note_index]
            notes_window = tk.Toplevel(root)
            notes_window.title(f"Note {note_index + 1}")
            notes_text = tk.Text(notes_window, bg="lightyellow", fg="blue")
            notes_text.insert(tk.END, note)
            notes_text.pack()
    except FileNotFoundError:
        messagebox.showwarning("No Notes Found", "There are no notes to display.")

root = tk.Tk()
root.title("Note-taking App")

# Text Entry
text_entry = tk.Text(root, height=10, width=40, bg="lightblue", fg="black")
text_entry.pack(pady=10)

# Buttons
save_button = tk.Button(root, text="Save Note", command=save_note, bg="green", fg="white")
view_button = tk.Button(root, text="View Notes", command=view_notes, bg="orange", fg="white")
clear_button = tk.Button(root, text="Clear Notes", command=clear_notes, bg="red", fg="white")
open_button = tk.Button(root, text="Open Note", command=open_specific_note, bg="purple", fg="white")

save_button.pack()
view_button.pack()
clear_button.pack()
open_button.pack()

root.mainloop()





























# COLOR MATHCH
import tkinter as tk
import tkinter.messagebox
import random

class ColorMatchGame:
    def __init__(self, master):
        self.master = master
        self.master.title("Color Match Game")
        self.score = 0
        self.wrong_attempts = 0
        self.colors = ["red", "green", "blue", "yellow", "purple", "orange","pink"]

        self.label = tk.Label(self.master, text="Match the color:")
        self.label.pack()

        self.score_label = tk.Label(self.master, text=f"Score: {self.score}", font=("Helvetica", 14))
        self.score_label.pack()

        self.attempts_label = tk.Label(self.master, text=f"Wrong Attempts: {self.wrong_attempts}", font=("Helvetica", 14))
        self.attempts_label.pack()

        self.match_label = tk.Label(self.master, text="", font=("Helvetica", 24))
        self.match_label.pack()

        self.color_buttons_frame = tk.Frame(self.master)
        self.color_buttons_frame.pack()

        self.color_buttons = []
        for color in self.colors:
            button = tk.Button(self.color_buttons_frame, text=color, bg=color, command=lambda c=color: self.check_color(c))
            button.pack(side=tk.LEFT, padx=10, pady=10)
            self.color_buttons.append(button)

        self.start_again_button = tk.Button(self.master, text="Start Again", command=self.start_again)
        self.start_again_button.pack()

        self.show_random_color()

    def show_random_color(self):
        random_color = random.choice(self.colors)
        self.match_label.config(text=random_color, fg=random.choice(self.colors))

    def check_color(self, selected_color):
        current_color = self.match_label.cget("text")
        if selected_color == current_color:
            self.score += 1
            self.wrong_attempts = 0
            self.score_label.config(text=f"Score: {self.score}")
        else:
            self.wrong_attempts += 1
            self.score -= 1
            self.attempts_label.config(text=f"Wrong Attempts: {self.wrong_attempts}")
            self.score_label.config(text=f"Score: {self.score}")
            if self.wrong_attempts == 3:
                self.game_over()
        self.show_random_color()

    def game_over(self):
        for button in self.color_buttons:
            button.config(state=tk.DISABLED)
        self.match_label.config(text="Game Over", fg="red")
        self.start_again_button.config(state=tk.NORMAL)
        tk.messagebox.showinfo("Game Over", f"Your final score: {self.score}")

    def start_again(self):
        for button in self.color_buttons:
            button.config(state=tk.NORMAL)
        self.score = 0
        self.wrong_attempts = 0
        self.score_label.config(text=f"Score: {self.score}")
        self.attempts_label.config(text=f"Wrong Attempts: {self.wrong_attempts}")
        self.start_again_button.config(state=tk.DISABLED)
        self.show_random_color()

if __name__ == "__main__":
    root = tk.Tk()
    game = ColorMatchGame(root)
    game.show_random_color()
    root.mainloop()























# TICTOCTOE

import tkinter as tk

root = tk.Tk()
root.title("Tic Tac Toe")

# Create variables to track the current player and the board state
current_player = "X"
board = [""] * 9

def check_win():
    for win_combination in [(0, 1, 2), (3, 4, 5), (6, 7, 8),
                            (0, 3, 6), (1, 4, 7), (2, 5, 8),
                            (0, 4, 8), (2, 4, 6)]:
        a, b, c = win_combination
        if board[a] == board[b] == board[c] != "":
            canvas.create_line(a % 3 * 100, a // 3 * 100, c % 3 * 100, c // 3 * 100, fill="red", width=5)
            canvas.create_text(150, 150, text=f"Player {board[a]} wins!", font=("Arial", 16))
            return True
    return False

def click(event):
    global current_player
    x, y = event.x // 100, event.y // 100
    index = x + y * 3

    if board[index] == "":
        board[index] = current_player
        if current_player == "X":
            canvas.create_text(x * 100 + 50, y * 100 + 50, text=current_player, font=("Arial", 36), fill="blue")
        else:
            canvas.create_text(x * 100 + 50, y * 100 + 50, text=current_player, font=("Arial", 36), fill="green")
        current_player = "O" if current_player == "X" else "X"
        if not check_win() and all(cell != "" for cell in board):
            canvas.create_text(150, 150, text="It's a tie!", font=("Arial", 16))

canvas = tk.Canvas(root, width=300, height=300)
canvas.pack()

canvas.create_line(100, 0, 100, 300, fill="black", width=2)
canvas.create_line(200, 0, 200, 300, fill="black", width=2)
canvas.create_line(0, 100, 300, 100, fill="black", width=2)
canvas.create_line(0, 200, 300, 200, fill="black", width=2)

canvas.bind("<Button-1>", click)

root.mainloop()























# UNIT CONVERTOR
def convert_weight():
    try:
        input_value = float(entry.get())
        from_unit = from_units.get()
        to_unit = to_units.get()

        if from_unit == "Kilograms" and to_unit == "Pounds":
            result = input_value * 2.20462
        elif from_unit == "Pounds" and to_unit == "Kilograms":
            result = input_value / 2.20462
        elif from_unit == "Kilograms" and to_unit == "Grams":
            result = input_value * 1000
        elif from_unit == "Grams" and to_unit == "Kilograms":
            result = input_value / 1000
        elif from_unit == "Pounds" and to_unit == "Ounces":
            result = input_value * 16
        elif from_unit == "Ounces" and to_unit == "Pounds":
            result = input_value / 16
        else:
            result = input_value

        result_label.config(text=f"Result: {result} {to_unit}", fg="green")
    except ValueError:
        result_label.config(text="Invalid input", fg="red")

# Create the main application window
root = tk.Tk()
root.title("Weight Converter")
root.geometry("550x250")

# Entry widget for input
entry = tk.Entry(root, width=15, font=("Arial", 12), bg="lightyellow", fg="black")
entry.grid(row=0, column=0, padx=10, pady=10)

# Dropdown menus for unit selection
from_units = tk.StringVar()
from_units.set("Kilograms")
to_units = tk.StringVar()
to_units.set("Pounds")

from_units_menu = tk.OptionMenu(root, from_units, "Kilograms", "Pounds", "Grams", "Ounces")
to_units_menu = tk.OptionMenu(root, to_units, "Kilograms", "Pounds", "Grams", "Ounces")

from_units_menu.config(font=("Arial", 12))
to_units_menu.config(font=("Arial", 12))

from_units_menu.grid(row=0, column=1, padx=10, pady=10)
to_units_menu.grid(row=0, column=2, padx=10, pady=10)

# Convert button
convert_button = tk.Button(root, text="Convert", command=convert_weight, font=("Arial", 12), bg="blue", fg="white")
convert_button.grid(row=0, column=3, padx=10, pady=10)

# Result label
result_label = tk.Label(root, text="Result:", font=("Arial", 14))
result_label.grid(row=1, column=0, columnspan=4, padx=10, pady=10)

# Start the application
root.mainloop()