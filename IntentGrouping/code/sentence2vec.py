
def sentence2vec(data, sentence, neighbors):
	'''
	@article{kiros2015skip,
	  title={Skip-Thought Vectors},
	  author={Kiros, Ryan and Zhu, Yukun and Salakhutdinov, Ruslan and Zemel, Richard S and Torralba, Antonio and Urtasun, Raquel and Fidler, Sanja},
	  journal={arXiv preprint arXiv:1506.06726},
	  year={2015}
	}
	'''

	import numpy as np
	import os.path
	import scipy.spatial.distance as sd
	import skipthoughts
	model = skipthoughts.load_model()
	encoder = skipthoughts.Encoder(model)

	encodings = encoder.encode(data)

	# A helper function to generate k nearest neighbors.
	def get_nn(sentence, k=10):
	  encoding = encoder.encode([sentence])
	  encoding = encoding[0]
	  scores = sd.cdist([encoding], encodings, "cosine")[0]
	  sorted_ids = np.argsort(scores)
	  print("Sentence : " + sentence)
	  print("\nNearest neighbors:")
	  for i in range(0, k):
		print(" %d. %s (%.3f)" %
			  (i+1, data[sorted_ids[i]], scores[sorted_ids[i]]))


	# Compute nearest neighbors of given sentence in the dataset.
	get_nn(sentence, neighbors)


if __name__ == "__main__":
	data =  ['Can you please tell me how old are you?',
                'What is your age?',
                'Please tell me your age.',
                'Please tell me how old are you?',
                'What is your age, please?',
                'Say me your age?',
                'How old are you?',
                'Roboy, how old are you?',
                'When were you born?',
                'What\'s the next birthday you celebrate?',
                
                'Who are you?',
                'Please tell me your name.',
                'What is your name?',
                'Tell me your name, please.',
                'How to call you?',
                'How can I call you?',
                'Do you have a name?',
                'What\'s the name you\'ve been given?',
                
                'Who are your creators?',
                'Where were you born?',
                'Who created you?',
                'Where were you assembled?',
                'Can you please let us know where were you assembled?',
                'Can you please let us know who created you?',
                'Can you let us know who created you?',
                'Do you know who developed you?',
                'Tell me how was you created?',
                'Whose your daddy?',
                'Where are you from?',
                
                'What can you do?',
                'What are you skilled at?',
                'How can you help me?',
                'Please tell me what can you do',
                'What did you learn?',
                'Can you really talk and understand or you just pretend?',
                'What are you capable of?',
                'What actions can you perform?',
                'Can you tell me about your functions?',
                'What are your abilities?',
                
                'How did you learn stuff?',
                'How have you come to this?',
                'From where did you learn things?',
                'Who teaches you all this?',
                'Who is your teacher?',
                'Who is your guru?',
                'Who is your mentor?',
                'How have you learnt all your skills?',
                'How do you know how to do stuff?',
                
                'How do you like this conference?',
                'Do you like this fair?',
                'What are your thoughts about the current exhibition?',
                'How is this evening for you?',
                'Do you feel comfortable here?',
                'Are you going to stay till the end?',
                'Why are you here?',
                
                'How is the weather today?',
                'What is best thing to do in the city today?',
                'Are you aware of any spectacular shows coming soon?',
                'Name me some historically important places?',
                'How is the road situation today?',
                'Any suggestions on how to spend the day?',
                'How far did you travel to this city?',
                
                'What is your goal?',
                'What do you live for?',
                'What were you created for?',
                'Why do you speak with people?',
                'What is your purpose?',
                'What is the purpose of your existence?',
                'What is the main reason for your creation?',
                'What is the sole purpose of your existence?',
                'Why are you so special?',
                                                
                'How old are you, Roboy?',
                'Tell me your name.',
                'Can you let me know who created you?',
                'What are your functions?',
                'Who taught you all the skills?',
                'What would you say about this meeting?',
                'Could you recommend some place to visit?',
                'Why were you created?']

	sentence = "Tell me how old are you?"
	neighbors = 10
    	sentence2vec(data, sentence, neighbors)
	
